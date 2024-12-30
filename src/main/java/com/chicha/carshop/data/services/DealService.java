package com.chicha.carshop.data.services;

import com.chicha.carshop.data.enities.Client;
import com.chicha.carshop.data.enities.Deal;
import com.chicha.carshop.data.enities.Payment;
import com.chicha.carshop.data.enities.Status;
import com.chicha.carshop.data.repos.DealRepository;
import com.chicha.carshop.data.repos.PaymentRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DealService {
    @Autowired
    DealRepository dealRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PaymentRepository paymentRepository;

    public void cancelDeal(int dealId) {
        Deal deal;
        Client client = clientService.findByEmail((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try{
            deal = dealRepository.findById(dealId).get();
            if (client != deal.getClient()) {
                throw new BadRequestException();
            }
        } catch (NoSuchElementException exception){
            exception.printStackTrace();
            return;
        } catch (BadRequestException e) {
            throw new RuntimeException("no such rights");
        }
        deal.setStatus(Status.builder().id(3).name("Canceled").build());
        dealRepository.save(deal);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

}
