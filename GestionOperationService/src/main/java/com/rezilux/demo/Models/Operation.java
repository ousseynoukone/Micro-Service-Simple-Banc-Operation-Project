package com.rezilux.demo.Models;


//import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long id;
    @Column

   private String numCompte;
    @Column
   private String typeOperation;
    @Column
   private  double montant ;

}
