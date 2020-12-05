package edu.baylor.ecs.rt.repository;

import edu.baylor.ecs.rt.model.auth.ERole;
import edu.baylor.ecs.rt.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
