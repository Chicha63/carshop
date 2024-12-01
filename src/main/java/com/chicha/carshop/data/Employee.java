package com.chicha.carshop.data;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false, length = 100)
    private String fullname;

    @ManyToOne
    @JoinColumn(name = "Accessright_Id", nullable = false)
    private AccessRight accessright;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 1000)
    private String password;

    // Getters and Setters
}
