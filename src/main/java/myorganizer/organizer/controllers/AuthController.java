package myorganizer.organizer.controllers;

import myorganizer.organizer.models.User;
import myorganizer.organizer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(value = "/sign-up")
  @ResponseStatus(ACCEPTED)
  public ResponseEntity<?> create(@RequestBody User user) {
    try {
      userRepository.save(user);
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