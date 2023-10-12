package com.rezilux.demo.Repository;

import com.rezilux.demo.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
