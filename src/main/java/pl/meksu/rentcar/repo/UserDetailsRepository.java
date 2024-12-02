package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.meksu.rentcar.models.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}