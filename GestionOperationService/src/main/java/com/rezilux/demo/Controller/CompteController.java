package com.rezilux.demo.Controller;

import com.rezilux.demo.Mappers.CompteMapper;
import com.rezilux.demo.Models.Client;
import com.rezilux.demo.Models.Compte;
import com.rezilux.demo.Repository.ClientRepository;
import com.rezilux.demo.Repository.CompteRepository;
import com.rezilux.demo.dtos.CompteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController

@RequestMapping("/go/comptes")
public class CompteController {
    @Autowired
    private final CompteRepository compteRepository;
    @Autowired

    private final ClientRepository clientRepository;
    @Autowired
    private CompteMapper compteMapper;


    @GetMapping
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = compteRepository.findAll();
        return ResponseEntity.ok(comptes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            return ResponseEntity.ok(compte.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Compte> createCompte(@RequestBody CompteDto compteDto) {
        Optional<Client> clientOptional = clientRepository.findById(compteDto.clientId());
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            Compte compte = compteMapper.compteDtoToCompte(compteDto);

            compte.setClient(client);
            Compte createdCompte = compteRepository.save(compte);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdCompte);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte updatedCompte) {
        Optional<Compte> existingCompte = compteRepository.findById(id);
        if (existingCompte.isPresent()) {
            updatedCompte.setId(id);
            Compte savedCompte = compteRepository.save(updatedCompte);
            return ResponseEntity.ok(savedCompte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        if (compte.isPresent()) {
            compteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
