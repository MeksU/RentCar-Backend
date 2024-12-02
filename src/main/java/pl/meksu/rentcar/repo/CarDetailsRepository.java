package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.meksu.rentcar.models.CarDetails;

public interface CarDetailsRepository extends JpaRepository<CarDetails, Integer> {
}
