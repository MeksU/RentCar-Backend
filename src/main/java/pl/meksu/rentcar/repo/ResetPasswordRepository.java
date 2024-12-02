package pl.meksu.rentcar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.meksu.rentcar.models.ResetPassword;
import pl.meksu.rentcar.models.User;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
    Optional<ResetPassword> findByUserAndResetCode(User user, String resetCode);

    Optional<ResetPassword> findByUser(User user);

    void deleteByExpiryDateBefore(Date expiryDate);
}
