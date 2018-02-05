package mumsched.repository;

import mumsched.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByFirstname(String firstname);
    Student findByUser(User user);
}