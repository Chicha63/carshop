package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Deal;
import com.chicha.carshop.data.repos.DealRepository;

import java.util.List;

public class DealService {
    DealRepository repository;
    public DealService(DealRepository repository) {
        this.repository = repository;
    }

    List<Deal> findAll() {
        return repository.findAll();
    }

    Deal findById(int id) {
        return repository.findById(id).orElse(null);
    }

    Deal save(Deal deal) {
        return repository.save(deal);
    }

    Deal update(Deal newDeal, int id) {
        return repository.findById(id)
                .map(deal -> {
                    deal = newDeal;
                    return repository.save(deal);
                })
                .orElseGet(()-> repository.save(newDeal));
    }

    void deleteById(int id) {
        repository.deleteById(id);
    }
}
