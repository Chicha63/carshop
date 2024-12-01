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
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 20)
    private String passportSeries;

    @Column(nullable = false, length = 50)
    private String passportNumber;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 16)
    private String phone;

    @Column(nullable = false, length = 50)
    private String email;

    // Getters and Setters
}