package com.chicha.carshop.data;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PossibleColor")
@IdClass(PossibleColorId.class)
public class PossibleColor {
    @Id
    @ManyToOne
    @JoinColumn(name = "Model_Id", nullable = false)
    private Model model;

    @Id
    @ManyToOne
    @JoinColumn(name = "Color_Id", nullable = false)
    private Color color;

    // Getters and Setters
}

// Composite Key Class
class PossibleColorId implements Serializable {
    private Integer model;
    private Integer color;

    // Equals and HashCode
}
