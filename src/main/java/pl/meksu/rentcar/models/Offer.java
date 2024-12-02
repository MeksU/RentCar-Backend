package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "offers")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private double price;

    @Column(length = 1000, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
