package myorganizer.organizer.controllers;

import myorganizer.organizer.models.User;
import myorganizer.organizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
  public ResponseEntity<?> signUp(@RequestBody User user) {
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

  @PostMapping(value = "/sign-in")
  @ResponseStatus(ACCEPTED)
  public ResponseEntity<?> signIn(@RequestBody User loginUser) {
    try {
      User user = userRepository.findByEmail(loginUser.getEmail());
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception ex) {
      String errorMessage;
      errorMessage = ex + " <== error";
      return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
}