package mumsched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
}