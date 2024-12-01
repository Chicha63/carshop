package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Good, Integer> {


}
