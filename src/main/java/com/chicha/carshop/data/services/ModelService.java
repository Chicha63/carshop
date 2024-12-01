package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Model;
import com.chicha.carshop.data.repos.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {
    ModelRepository modelRepository;
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    Model save(Model model) {
        return modelRepository.save(model);
    }
    List<Model> findAll() {
        return modelRepository.findAll();
    }
    Model findById(int id) {
        return modelRepository.findById(id).orElse(null);
    }
    void delete(int id) {
        modelRepository.deleteById(id);
    }
    Model update(Model newModel, int id) {
        return modelRepository.findById(id)
                .map(model -> {
                    model = newModel;
                    return modelRepository.save(model);
                })
                .orElseGet(()->modelRepository.save(newModel));
    }
}
