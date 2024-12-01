package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {


}
