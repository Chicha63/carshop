package com.chicha.carshop.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Good_Id", nullable = false)
    private Good good;

    @ManyToOne
    @JoinColumn(name = "Client_Id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "Payment_Id", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "Employee_Id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Boolean delivery;

    // Getters and Setters
}
