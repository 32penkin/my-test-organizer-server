package myorganizer.organizer.repositories;

import myorganizer.organizer.models.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
  AppUser findByEmail(String email);

  AppUser save(AppUser user);
}
