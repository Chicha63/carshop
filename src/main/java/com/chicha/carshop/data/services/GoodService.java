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

    public Good save(Good good) {
        return goodRepository.save(good);
    }
    public List<Good> findAll() {
        return goodRepository.findAll();
    }
    public Good findById(int id) {
        return goodRepository.findById(id).orElse(null);
    }
    public Good update(Good newGood, int id) {
        return goodRepository.findById(id)
                .map(good -> {
                    good = newGood;
                    return goodRepository.save(good);
                })
                .orElse(goodRepository.save(newGood));
    }
    public void delete(int id) {
        goodRepository.deleteById(id);
    }
}
