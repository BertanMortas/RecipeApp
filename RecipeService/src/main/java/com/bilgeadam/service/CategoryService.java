package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateCategoryRequestDto;
import com.bilgeadam.dto.request.UpdateCategoryRequestDto;
import com.bilgeadam.entity.Category;
import com.bilgeadam.entity.enums.ERole;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.RecipeManagerException;
import com.bilgeadam.mapper.ICategoryMapper;
import com.bilgeadam.repository.ICategoryRepository;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class CategoryService extends ServiceManager<Category, String> {
    private final ICategoryRepository categoryRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public CategoryService(ICategoryRepository categoryRepository, JwtTokenProvider jwtTokenProvider) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Category createCategory(CreateCategoryRequestDto dto) {
        roleAndTokenControl(dto.getToken());
        dto.setName(dto.getName().toUpperCase(Locale.ENGLISH));
        return save(ICategoryMapper.INSTANCE.toCategory(dto));
    }

    public Category updateCategory(UpdateCategoryRequestDto dto) {
        roleAndTokenControl(dto.getToken());
        Optional<Category> category = categoryRepository.findById(dto.getId());
        if (category.isEmpty()) {
            throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
        }

        category.get().setName(dto.getName().toUpperCase(Locale.ENGLISH));
        return update(category.get());
    }
    public void deleteCategory(UpdateCategoryRequestDto dto){
        // TODO make delete DTO class
        roleAndTokenControl(dto.getToken());
        deleteById(dto.getId());
    }
    public Category findByName(String name){
        Optional<Category> category = categoryRepository.findByName(name.toUpperCase(Locale.ENGLISH));
        if (category.isEmpty()) {
            throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
        }
        return category.get();
    }
    public void roleAndTokenControl(String token){
        Optional<String> role = jwtTokenProvider.getRoleFromToken(token);
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new RecipeManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!role.get().equals(ERole.ADMIN.toString())) {
            throw new RecipeManagerException(ErrorType.AUTHORIZATION_ERROR);
        }
    }

}
