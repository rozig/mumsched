package mumsched.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByFirstname(String firstname);
}