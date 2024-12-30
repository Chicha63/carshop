package com.chicha.carshop.data.DTO;

import com.chicha.carshop.data.enities.Color;
import com.chicha.carshop.data.enities.Good;
import com.chicha.carshop.data.enities.Payment;
import com.chicha.carshop.data.enities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealDTO {
    private boolean delivery;
    private int payment;
    private Good good;
    private Status status;
    private Instant date;
    private BigDecimal price;
}
