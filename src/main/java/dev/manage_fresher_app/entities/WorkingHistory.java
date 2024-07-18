package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WorkingHistory")
public class WorkingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startTime;
    private Date endTime;
    private String status;

    @ManyToOne
    @JoinColumn(name = "fresher_id")
    private Fresher fresher;

    @OneToMany(mappedBy = "history")
    private Set<ExerciseResult> results;
}
