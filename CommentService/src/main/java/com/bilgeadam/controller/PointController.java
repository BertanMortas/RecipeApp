package com.bilgeadam.controller;
import com.bilgeadam.dto.request.CreatePointRequestDto;
import com.bilgeadam.dto.request.DeletePointRequestDto;
import com.bilgeadam.dto.request.UpdatePointRequestDto;
import com.bilgeadam.entity.Point;
import com.bilgeadam.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(POINT)
public class PointController {
    private final PointService pointService;

    @PostMapping(CREATE)
    public ResponseEntity<Point> createPoint(@RequestBody @Valid CreatePointRequestDto dto){
        return ResponseEntity.ok(pointService.createPoint(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Point> updatePoint(@RequestBody @Valid UpdatePointRequestDto dto){
        return ResponseEntity.ok(pointService.updatePoint(dto));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deletePoint(@RequestBody DeletePointRequestDto dto){
        return ResponseEntity.ok(pointService.deletePoint(dto));
    }
}
