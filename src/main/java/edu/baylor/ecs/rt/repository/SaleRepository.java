package edu.baylor.ecs.rt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import edu.baylor.ecs.rt.model.Sale;


//@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {



	
	

}
