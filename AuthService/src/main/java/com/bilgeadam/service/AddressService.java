package com.bilgeadam.service;

import com.bilgeadam.dto.request.UpdateAddressDto;
import com.bilgeadam.entity.Address;
import com.bilgeadam.mapper.IAddressMapper;
import com.bilgeadam.repository.IAddressRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService extends ServiceManager<Address,Long> {
    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository) {
        super(addressRepository);
        this.addressRepository = addressRepository;
    }

    public Boolean updateAddress(UpdateAddressDto dto) {
        Optional<Address> address = addressRepository.findByAuthId(dto.getAuthId());
        if (address.isEmpty()) {
            save(IAddressMapper.INSTANCE.toAddress(dto));
            return true;
        } else {
            update(IAddressMapper.INSTANCE.updateAddressFromDto(dto,address.get()));
            return true;
        }
    }
}
