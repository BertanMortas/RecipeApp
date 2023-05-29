package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateCategoryRequestDto;
import com.bilgeadam.dto.request.UpdateCategoryRequestDto;
import com.bilgeadam.entity.Category;
import com.bilgeadam.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping(CREATE)
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Category> updateCategory(@RequestBody UpdateCategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.updateCategory(dto));
    }
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Void> deleteCategory(@RequestBody UpdateCategoryRequestDto dto){
        categoryService.deleteCategory(dto);
        return ResponseEntity.ok().build();
    }
}
