package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Fresher extends Employee{
    private Double avgScore;
}
