package com.bilgeadam.manager;

import com.bilgeadam.dto.request.UpdateAddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bilgeadam.constant.ApiUrls.UPDATE;

@FeignClient(name = "address-service", url = "http://localhost:9090/api/v1/address")
public interface IAddressManager {
    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> updateAddress(@RequestBody UpdateAddressDto dto);
}
