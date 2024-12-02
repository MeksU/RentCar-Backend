package pl.meksu.rentcar.repo;

import pl.meksu.rentcar.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(int userId);
    List<Reservation> findByOfferId(int offerId);
}