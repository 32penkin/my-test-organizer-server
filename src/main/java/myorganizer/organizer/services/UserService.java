package myorganizer.organizer.services;

import myorganizer.organizer.models.AppUser;

public interface UserService {
  AppUser save(AppUser user);

  AppUser findByEmail(String email);
}