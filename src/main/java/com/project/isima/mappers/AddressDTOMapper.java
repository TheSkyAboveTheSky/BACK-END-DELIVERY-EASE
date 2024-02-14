package com.project.isima.mappers;

import com.project.isima.dtos.AddressDTO;
import com.project.isima.entities.Address;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressDTOMapper implements Function<Address, AddressDTO> {
    @Override
    public AddressDTO apply(Address address) {
        return new AddressDTO(address.getId(), address.getCity());
    }
}
