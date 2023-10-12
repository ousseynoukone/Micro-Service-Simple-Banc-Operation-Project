package com.rezilux.demo.Mappers;

import com.rezilux.demo.Models.Compte;
import com.rezilux.demo.dtos.CompteDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CompteMapper {
    Compte compteDtoToCompte (CompteDto compteDto);

}
