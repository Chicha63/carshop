package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Good;
import com.chicha.carshop.data.repos.GoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {
    GoodRepository goodRepository;
    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    Good save(Good good) {
        return goodRepository.save(good);
    }
    List<Good> findAll() {
        return goodRepository.findAll();
    }
    Good findById(int id) {
        return goodRepository.findById(id).orElse(null);
    }
    Good update(Good newGood, int id) {
        return goodRepository.findById(id)
                .map(good -> {
                    good = newGood;
                    return goodRepository.save(good);
                })
                .orElse(goodRepository.save(newGood));
    }
    void delete(int id) {
        goodRepository.deleteById(id);
    }
}
