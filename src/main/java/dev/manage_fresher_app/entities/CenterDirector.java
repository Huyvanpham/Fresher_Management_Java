package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CenterDirector extends Employee{
    private int timeOfTakingOffice;
}
