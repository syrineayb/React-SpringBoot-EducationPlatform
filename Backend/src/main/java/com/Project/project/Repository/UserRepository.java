package com.Project.project.Repository;


import com.Project.project.models.ERole;
import com.Project.project.models.Role;
import com.Project.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  List<User> findByRolesContaining(Role role);
  @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
  List<User> findByRoleName(@Param("roleName") ERole roleName);

}
