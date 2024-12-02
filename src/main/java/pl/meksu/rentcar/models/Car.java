package pl.meksu.rentcar.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(length = 255)
    private String image;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonManagedReference
    private CarDetails carDetails;
}
