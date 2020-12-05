package edu.baylor.ecs.rt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.model.auth.User;

//@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
	Optional<License> findById(long id);
}

