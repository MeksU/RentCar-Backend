package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.meksu.rentcar.models.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
}