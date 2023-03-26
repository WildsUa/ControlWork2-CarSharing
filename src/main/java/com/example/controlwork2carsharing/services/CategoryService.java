package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Category;
import com.example.controlwork2carsharing.repositories.CategoryRepository;
import com.example.controlwork2carsharing.validators.EntityValidator;
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
        Message result = new EntityValidator().validateCategory(category);
        if (result.getWebclass().equals("alert alert-success")) {
            if (category.getId() == null) {
                result.setText("Category was added successful");
                categoryRepository.save(category);
            } else if (this.findCategoryByID(category.getId()).isEmpty()) {
                result = new Message("No such category in the list", "alert alert-warning");
            } else {
                result.setText("Category was updated successful");
                categoryRepository.save(category);
            }
        }
        return result;
    }
}
