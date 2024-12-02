package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 40)
    private String surname;

    @Column(length = 100)
    private String mail;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String type;
}
