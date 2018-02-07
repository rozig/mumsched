package mumsched.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Course;
import mumsched.entity.Faculty;
import mumsched.entity.User;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByFirstname(String firstname);
    Faculty findByUser(User user);
    List<Faculty> findByCourses(Course course);
}