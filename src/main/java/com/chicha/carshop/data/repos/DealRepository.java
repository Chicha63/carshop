package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.enities.Client;
import com.chicha.carshop.data.enities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Integer> {

    public List<Deal> findByClient(Client client);
}
