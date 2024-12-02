package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 50)
    private String paymentStatus;
}
