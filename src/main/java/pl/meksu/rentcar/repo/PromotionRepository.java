package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.meksu.rentcar.models.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}