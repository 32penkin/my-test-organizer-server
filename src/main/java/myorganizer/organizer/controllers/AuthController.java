package myorganizer.organizer.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import myorganizer.organizer.models.AppUser;
import myorganizer.organizer.models.TokenEntity;
import myorganizer.organizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.ServletException;

import java.util.Date;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostMapping(value = "/sign-up")
  @ResponseStatus(ACCEPTED)
  public ResponseEntity<?> signUp(@RequestBody AppUser user) {
    try {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception ex) {
      String errorMessage;
      errorMessage = ex + " <== error";
      return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<String> login(@RequestBody AppUser login) throws ServletException {
    String jwtToken = "";
    if (login.getEmail() == null || login.getPassword() == null) {
      throw new ServletException("Please fill in username and password");
    }
    String email = login.getEmail();
    String password = login.getPassword();
    AppUser user = userRepository.findByEmail(email);
    if (user == null) {
      throw new ServletException("User email not found.");
    }
    String pwd = user.getPassword();
    if (!bCryptPasswordEncoder.matches(password, pwd)) {
      throw new ServletException("Invalid login. Please check your name and password.");
    }
    jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
      .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

    return new ResponseEntity<String>(new TokenEntity(jwtToken).toString(), HttpStatus.OK);
  }

  @RequestMapping(value = "/sign-out", method = RequestMethod.DELETE)
  public @ResponseBody HttpStatus logout() {
    return HttpStatus.OK;
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<AppUser> getAllUsers() {
    return userRepository.findAll();
  }
}