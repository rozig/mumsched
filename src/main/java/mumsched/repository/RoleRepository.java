package mumsched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByRole(String role);
}