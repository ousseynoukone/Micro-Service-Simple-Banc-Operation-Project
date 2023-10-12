package com.rezilux.demo.Repository;

import com.rezilux.demo.Models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository  extends JpaRepository<Compte,Long> {
    Compte findByNumCompte(String numCompte);

}
