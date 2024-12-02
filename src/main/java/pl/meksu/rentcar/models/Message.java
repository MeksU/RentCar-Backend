package pl.meksu.rentcar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;
}
