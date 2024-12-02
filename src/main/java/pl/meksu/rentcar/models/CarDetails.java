package pl.meksu.rentcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "car_details")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 5)
    private String engine;

    @Column(nullable = false)
    private int power;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 50)
    private String fuelType;

    @Column(nullable = false, length = 50)
    private String transmission;

    @Column(nullable = false)
    private int seats;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false, unique = true)
    @JsonBackReference
    private Car car;
}