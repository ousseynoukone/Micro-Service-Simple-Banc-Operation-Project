package com.rezilux.demo.Repository;

import com.rezilux.demo.Models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
