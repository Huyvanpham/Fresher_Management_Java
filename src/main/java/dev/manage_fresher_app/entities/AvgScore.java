package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AvgScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double avgScore;

    @OneToOne
    @JoinColumn(name = "fresher_id")
    private Fresher fresher;
}
