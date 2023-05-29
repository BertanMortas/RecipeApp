package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateRecipeRequestDto;
import com.bilgeadam.dto.request.DeleteRecipeRequestDto;
import com.bilgeadam.dto.request.FilterRecipeRequestDto;
import com.bilgeadam.dto.request.UpdateRecipeRequestDto;
import com.bilgeadam.dto.response.FindRecipeByIdResponseDto;
import com.bilgeadam.dto.response.FindUserByCategoryIdResponseDto;
import com.bilgeadam.entity.Category;
import com.bilgeadam.entity.Recipe;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.RecipeManagerException;
import com.bilgeadam.manager.IUserprofileManager;
import com.bilgeadam.mapper.IRecipeMapper;
import com.bilgeadam.rabbitmq.producer.MailNewRecipeProducer;
import com.bilgeadam.repository.IRecipeRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService extends ServiceManager<Recipe,String> {
    private final IRecipeRepository recipeRepository;
    private final IUserprofileManager userprofileManager;
    private final CategoryService categoryService;
    private final MailNewRecipeProducer mailNewRecipeProducer;

    public RecipeService(IRecipeRepository recipeRepository, IUserprofileManager userprofileManager, CategoryService categoryService, MailNewRecipeProducer mailNewRecipeProducer) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
        this.userprofileManager = userprofileManager;
        this.categoryService = categoryService;
        this.mailNewRecipeProducer = mailNewRecipeProducer;
    }

    @Caching(evict = {
            @CacheEvict(value = "findAll", allEntries = true),
            @CacheEvict(value = "find-by-id", allEntries = true)
    })
    public Recipe createRecipe(CreateRecipeRequestDto dto){
        categoryService.roleAndTokenControl(dto.getToken());
        Recipe recipe = IRecipeMapper.INSTANCE.toRecipe(dto);
        // Todo redis on category service
        Optional<Category> category = categoryService.findById(dto.getCategoryId());
        if (category.isEmpty()) {
            throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
        }
        //find the categoryId which added to users favorites and return email vs...
        List<FindUserByCategoryIdResponseDto> dtoList =userprofileManager.findUserByCategoryId(dto.getCategoryId()).getBody();
        dtoList.stream().forEach(x->x.setRecipeName(recipe.getName()));
        // send dto list to mail service
        dtoList.stream().forEach(x-> mailNewRecipeProducer.sendNewRecipe(IRecipeMapper.INSTANCE.dtoToModel(x)));
        return save(recipe);
    }
    @Caching(evict = {
            @CacheEvict(value = "findAll", allEntries = true),
            @CacheEvict(value = "find-by-id", allEntries = true)
    })
    public Recipe updateRecipe(String recipeId, UpdateRecipeRequestDto dto){
        categoryService.roleAndTokenControl(dto.getToken());
        // redis
        Optional<Recipe> recipe = findByIdRedis(recipeId);
        if (recipe.isEmpty()) {
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        }
        return update(IRecipeMapper.INSTANCE.toRecipe(dto,recipe.get()));
    }
    @Caching(evict = {
            @CacheEvict(value = "findAll", allEntries = true),
            @CacheEvict(value = "find-by-id", allEntries = true)
    })
    public Boolean deleteRecipe(DeleteRecipeRequestDto dto){
        categoryService.roleAndTokenControl(dto.getToken());
        // redis
        Optional<Recipe> recipe = findByIdRedis(dto.getRecipeId());
        if (recipe.isEmpty()) {
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        }
        recipe.get().setVisibility(false);
        update(recipe.get());
        return true;
    }
    public List<Recipe> recipeFilter(FilterRecipeRequestDto dto){
        // TODO bütün db yi çekme az ve ihtiyacın olan db yi çek sonra findbyid ile gerkli bilgileri sunmayaçalış
        // findall ile çektikten sonra hashmap belki hızlandırabilir ??
        //List<Recipe> recipeList= findAll(); // 1000
        List<Recipe> recipeList= findAllRedis();
        // findAll ile hepsi değil listelenecek bilgiler de çekilebilir !!!!!! view ile  !!!!!!
        List<String> properties = new ArrayList<>();
        properties.add(dto.getCategoryId());
        properties.add(dto.getName());
        properties.add(dto.getIngredientsName());
        properties.add(dto.getIngredientsQuantity());
        properties.add(dto.getNutritionalValueCalorie());
        // todo foreach le properties a bakıp ona göre filtreleri çağır
        //  treeSet içine liste vermeye bakın
        if (properties.isEmpty()) {
            return recipeList;
        }
        if (dto.getCategoryId() != null) {
            recipeList = recipeCategoryFilter(recipeList,dto.getCategoryId()); // 300
        }
        if (dto.getName() != null) {
            recipeList = recipeNameFilter(recipeList,dto.getName()); //100
        }
        if (dto.getIngredientsName() != null) {
            recipeList = recipeIngredientsNameFilter(recipeList,dto.getIngredientsName()); //100
        }
        if (dto.getIngredientsQuantity() != null) {
            recipeList = recipeIngredientsQuantityFilter(recipeList,dto.getIngredientsQuantity()); //100
        }
        if (dto.getNutritionalValueCalorie() != null) {
            recipeList = recipeNutritionalValueCalorieFilter(recipeList,dto.getNutritionalValueCalorie()); //100
        }
        return recipeList;
    }
    public List<Recipe> recipeCategoryFilter(List<Recipe> recipeList,String categoryId){
        // TODO try search on DB
        return recipeList.parallelStream().filter(x -> x.getCategoryId().equals(categoryId)).collect(Collectors.toList());
    }
    public List<Recipe> recipeNameFilter(List<Recipe> recipeList,String name){
        return recipeList.parallelStream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
    }
    public List<Recipe> recipeIngredientsNameFilter(List<Recipe> recipeList,String name){
        return recipeList.parallelStream().filter(x -> x.getIngredient().getIngredientsName().equals(name)).collect(Collectors.toList());
    }
    public List<Recipe> recipeIngredientsQuantityFilter(List<Recipe> recipeList,String quantity){
        return recipeList.parallelStream().filter(x -> x.getIngredient().getQuantity().equals(quantity)).collect(Collectors.toList());
    }
    public List<Recipe> recipeNutritionalValueCalorieFilter(List<Recipe> recipeList,String calorie){
        return recipeList.parallelStream().filter(x -> x.getNutritionalValue().getCalorie().equals(calorie)).collect(Collectors.toList());
        // for filter process if owner want a space codes below are be refactor and then will use
        /** TODO for calorie do this
         * public boolean isEligibleForScholarship() {
         *     return getMarksAverage() > 50
         *       && marks.size() > 3
         *       && profile != Profile.PHYSICS;
         * }
         * public void whenUsingSingleComplexFilterExtracted_dataShouldBeFiltered() {
         *     List<Student> filteredStream = students.stream()
         *         .filter(Student::isEligibleForScholarship)
         *         .collect(Collectors.toList());
         *
         *     assertThat(filteredStream).containsExactly(mathStudent);
         * }
         */
    }
    public FindRecipeByIdResponseDto findRecipeById(String recipeId){ // this method add favorite to user
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        }
        return IRecipeMapper.INSTANCE.toDto(recipe.get());
    }
    @Cacheable(value = "findAll")
    public List<Recipe> findAllRedis(){
        return findAll();
    }
    @Cacheable(value = "find-by-id", key = "#recipeId")
    public Optional<Recipe> findByIdRedis(String recipeId){
        return recipeRepository.findById(recipeId);
    }




}
