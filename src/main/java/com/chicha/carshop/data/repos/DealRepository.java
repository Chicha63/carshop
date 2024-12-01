package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DealRepository extends JpaRepository<Deal, Integer> {


}
