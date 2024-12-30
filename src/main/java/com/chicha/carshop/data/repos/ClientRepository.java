package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.enities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    public Client findByEmail(String email);
}
