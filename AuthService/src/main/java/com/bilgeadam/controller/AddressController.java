package com.bilgeadam.controller;

import com.bilgeadam.dto.request.UpdateAddressDto;
import com.bilgeadam.entity.Address;
import com.bilgeadam.service.AddressService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADDRESS)
public class AddressController {
    private final AddressService addressService;

    @PostMapping(UPDATE)
    @Hidden
    public ResponseEntity<Boolean> updateAddress(@RequestBody UpdateAddressDto dto){
        return ResponseEntity.ok(addressService.updateAddress(dto));
    }
}
