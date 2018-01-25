package myorganizer.organizer.repositories;

import myorganizer.organizer.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}