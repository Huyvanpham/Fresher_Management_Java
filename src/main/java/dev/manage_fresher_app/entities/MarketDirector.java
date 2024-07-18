package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MarketDirector extends Employee{
    private int workExperience;

    @OneToOne
    @JoinColumn(name = "market_id")
    private Market market;
}
