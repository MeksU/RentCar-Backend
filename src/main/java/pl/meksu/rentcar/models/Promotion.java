package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "promotions")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int discountPercentage;

    @Column(length = 500)
    private String description;
}
