package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.meksu.rentcar.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}