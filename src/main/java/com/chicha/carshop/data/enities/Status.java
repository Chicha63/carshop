package com.chicha.carshop.data.enities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class Status {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;
}