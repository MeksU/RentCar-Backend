package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(nullable = false, length = 50)
    private String paymentNumber;
}