package com.chicha.carshop.data.services;

import com.chicha.carshop.data.enities.*;
import com.chicha.carshop.data.repos.DealRepository;
import com.chicha.carshop.data.repos.GoodRepository;
import com.chicha.carshop.data.repos.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Good> getFilteredGoods(
            BigDecimal priceMin,
            BigDecimal priceMax,
            Integer colorId,
            Integer countryId,
            Integer availabilityId,
            Integer manufacturerId,
            String modelName,
            Integer modelYear,
            Float engineVolumeMin,
            Float engineVolumeMax,
            String engineName,
            Integer bodyTypeId,
            Integer enginePlacementId,
            Integer doorsCount,
            Integer placesCount
    ) {
        return goodRepository.findFilteredGoods(
                priceMin, priceMax, colorId, countryId, availabilityId, manufacturerId,
                modelName, modelYear, engineVolumeMin, engineVolumeMax, engineName,
                bodyTypeId, enginePlacementId, doorsCount, placesCount
        );
    }


    public Deal buy(int goodId, boolean delivery, int payment){
        Status status = Status.builder()
                .id(1)
                .name("Pending...")
                .build();
        Good good = goodRepository.findById(goodId).get();
        Payment payment1 = paymentRepository.findById(payment).get();
        Client client = clientService.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Deal deal = Deal.builder()
                .good(good)
                .client(client)
                .date(Instant.now())
                .delivery(delivery)
                .payment(payment1)
                .status(status)
                .finalPrice(good.getPrice())
                .build();
        dealRepository.save(deal);
        good.setAvailability(Availability.builder().id(2).type("Sold").build());
        goodRepository.save(good);
        return deal;
    }



}