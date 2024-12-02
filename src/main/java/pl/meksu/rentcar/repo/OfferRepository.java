package pl.meksu.rentcar.repo;

import pl.meksu.rentcar.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}