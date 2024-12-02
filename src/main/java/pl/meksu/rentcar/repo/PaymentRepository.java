package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.meksu.rentcar.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}