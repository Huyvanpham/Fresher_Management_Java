package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date startDate;
    private Date endDate;
    private String description;
    private String status;

    private String file;

    @OneToMany(mappedBy = "exercise")
    private List<ExerciseResult> exerciseResults;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
