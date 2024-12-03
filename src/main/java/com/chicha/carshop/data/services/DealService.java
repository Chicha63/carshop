package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Deal;
import com.chicha.carshop.data.repos.DealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {
    DealRepository repository;
    public DealService(DealRepository repository) {
        this.repository = repository;
    }

    public List<Deal> findAll() {
        return repository.findAll();
    }

    public Deal findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Deal save(Deal deal) {
        return repository.save(deal);
    }

    public Deal update(Deal newDeal, int id) {
        return repository.findById(id)
                .map(deal -> {
                    deal = newDeal;
                    return repository.save(deal);
                })
                .orElseGet(()-> repository.save(newDeal));
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
