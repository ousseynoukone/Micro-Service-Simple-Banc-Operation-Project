package com.rezilux.demo.Mappers;

import com.rezilux.demo.Models.Operation;
import com.rezilux.demo.dtos.OperationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface OperationMapper {
    Operation operationDtoToOperation (OperationDto operationDto);

}
