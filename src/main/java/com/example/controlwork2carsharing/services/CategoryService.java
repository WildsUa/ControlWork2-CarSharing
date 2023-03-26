package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Category;
import com.example.controlwork2carsharing.repositories.CategoryRepository;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findCategoryByID(int id){
        return categoryRepository.findById(id);
    }

    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    public Message saveCategory(Category category){
        Message result;
        if (category.getId() == null) {
            result = new Message("Category was added successful", "alert alert-success");
            categoryRepository.save(category);
        } else if (this.findCategoryByID(category.getId()).isEmpty()){
            result = new Message("No such category in the list", "alert alert-warning");
        } else {
            result = new Message("Category was updated successful", "alert alert-success");
            categoryRepository.save(category);
        }
        return result;
    }
}
