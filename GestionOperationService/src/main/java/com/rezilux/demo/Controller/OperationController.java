package com.rezilux.demo.Controller;

import com.rezilux.demo.Mappers.CompteMapper;
import com.rezilux.demo.Mappers.OperationMapper;
import com.rezilux.demo.Models.Compte;
import com.rezilux.demo.Models.Operation;
import com.rezilux.demo.Repository.CompteRepository;
import com.rezilux.demo.Repository.OperationRepository;
import com.rezilux.demo.dtos.OperationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/go/operations")
public class OperationController {
    @Autowired

    private final OperationRepository operationRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationMapper operationMapper;

    @GetMapping
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperationById(@PathVariable Long id) {
        Optional<Operation> operation = operationRepository.findById(id);
        if (operation.isPresent()) {
            return ResponseEntity.ok(operation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> createOperation(@RequestBody OperationDto operationDto) {
        Compte compte = compteRepository.findByNumCompte(operationDto.numCompte());
        if (operationDto.typeOperation().equals("credit")) {
            compte.setSolde(compte.getSolde() + operationDto.montant());
        } else if (operationDto.typeOperation().equals("debit")) {
            if (compte.getSolde() < operationDto.montant()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("solde inssufisant");
            } else {
                compte.setSolde(compte.getSolde() - operationDto.montant());
            }
        }

        compteRepository.save(compte); // Update the Compte entity

        Operation createdOperation = operationRepository.save(operationMapper.operationDtoToOperation(operationDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperation);
    }





    @PutMapping("/{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable Long id, @RequestBody Operation updatedOperation) {
        Optional<Operation> existingOperation = operationRepository.findById(id);
        if (existingOperation.isPresent()) {
            updatedOperation.setId(id);
            Operation savedOperation = operationRepository.save(updatedOperation);
            return ResponseEntity.ok(savedOperation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        Optional<Operation> operation = operationRepository.findById(id);
        if (operation.isPresent()) {
            operationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
