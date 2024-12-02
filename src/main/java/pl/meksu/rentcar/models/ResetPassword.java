package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String resetCode;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    public ResetPassword(User user, String resetCode, Date expiryDate) {
        this.user = user;
        this.resetCode = resetCode;
        this.expiryDate = expiryDate;
    }
}