package dev.manage_fresher_app.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
//    @Id
//    private String email;
//    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        private String email;
        private String password;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "employee_id", referencedColumnName = "id")
        private Employee employee;
}
