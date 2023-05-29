package com.bilgeadam.repository;

import com.bilgeadam.entity.Userprofile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserprofileRepository extends MongoRepository<Userprofile,String> {
    Optional<Userprofile> findByAuthId(Long authId);
    List<Userprofile> findAllByFavoriteRecipeCategoryList(String categoryId);
}
