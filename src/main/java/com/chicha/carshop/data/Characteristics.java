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
@Table(name = "Characteristics")
public class Characteristics {
    @Id
    private Integer goodId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "Good_Id")
    private Good good;

    @ManyToOne
    @JoinColumn(name = "BodyType_Id", nullable = false)
    private BodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "Engine_Id", nullable = false)
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "EnginePlacement_Id", nullable = false)
    private EnginePlacement enginePlacement;

    @Column(nullable = false)
    private Integer doorsCount;

    @Column(nullable = false)
    private Integer placesCount;

    // Getters and Setters
}