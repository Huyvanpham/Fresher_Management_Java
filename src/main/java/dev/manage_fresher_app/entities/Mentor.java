package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mentor")
public class Mentor extends Employee{
    private String position;
}
