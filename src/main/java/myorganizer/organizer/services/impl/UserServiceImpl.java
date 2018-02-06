package myorganizer.organizer.services.impl;

import myorganizer.organizer.models.AppUser;
import myorganizer.organizer.repositories.UserRepository;
import myorganizer.organizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userDao;

  public AppUser save(AppUser user) {
    return userDao.save(user);
  }

  public AppUser findByEmail(String email) {
    return userDao.findByEmail(email);
  }
}
