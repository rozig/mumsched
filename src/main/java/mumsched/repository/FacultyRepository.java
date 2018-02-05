package mumsched.repository;

import mumsched.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByFirstname(String firstname);
    Faculty findByUser(User user);
}