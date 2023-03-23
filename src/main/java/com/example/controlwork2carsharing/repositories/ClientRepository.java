package com.example.controlwork2carsharing.repositories;

import com.example.controlwork2carsharing.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    //@Query ("select c from Client c where not(c.id = (select o.client.id from Order o where o.endDate is null))")
    @Query("SELECT c FROM Client c LEFT JOIN Order o ON c.id = o.client.id AND o.endDate IS NULL WHERE o.id IS NULL ")
    List<Client> findFreeClients();
}