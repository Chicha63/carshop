package com.chicha.carshop.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Good")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "Color_Id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "Country_Id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "Manufacturer_Id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "Model_Id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "Availability_Id", nullable = false)
    private Availability availability;

    // Getters and Setters
}
