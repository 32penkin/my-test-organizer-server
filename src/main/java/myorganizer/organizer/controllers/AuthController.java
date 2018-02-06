package myorganizer.organizer.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import myorganizer.organizer.models.AppUser;
import myorganizer.organizer.repositories.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

//  @PostMapping(value = "/sign-in")
//  @ResponseStatus(ACCEPTED)
//  public ResponseEntity<?> signIn(@RequestBody AppUser loginUser) throws ServletException {
//    try {
//      AppUser user = userRepository.findByEmail(loginUser.getEmail());
//      return new ResponseEntity<>(user, HttpStatus.OK);
//    } catch (Exception ex) {
//      String errorMessage;
//      errorMessage = ex + " <== error";
//      return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }
//  }


  @RequestMapping(value = "/sign-in", method = RequestMethod.POST, produces = "application/json")
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
    System.out.print(jwtToken);

    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);

    return new ResponseEntity<String>(new TokenEntity(jwtToken).toString() , httpHeaders, HttpStatus.OK);
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<AppUser> getAllUsers() {
    return userRepository.findAll();
  }
}

class TokenEntity {
  private String token;

  @Override
  public String toString() {
    return "{\"token\":" + "\"" + token + "\"}";
  }

  public TokenEntity(String token) {
    this.token = token;
  }
}