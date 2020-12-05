package edu.baylor.ecs.rt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.baylor.ecs.rt.model.Payment;
import edu.baylor.ecs.rt.model.Sale;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
