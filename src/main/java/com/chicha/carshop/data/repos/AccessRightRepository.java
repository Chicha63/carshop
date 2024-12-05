package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.Accessright;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRightRepository extends JpaRepository<Accessright, Integer> {

}
