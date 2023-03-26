package com.example.controlwork2carsharing.controllers;

import com.example.controlwork2carsharing.entities.Category;
import com.example.controlwork2carsharing.services.CategoryService;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getCategories (Model model){
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }
    @GetMapping("/category/{id}")
    public String categoryPage(@PathVariable("id") int id, Model model) {
        Optional<Category> category = categoryService.findCategoryByID(id);
        if (category.isEmpty()) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category.get());
        return "category";
    }

    @PostMapping("/add_category")
    public String addCategory(@ModelAttribute( value = "categories" ) Category category, RedirectAttributes redirectAttributes){

        Message message = categoryService.saveCategory(category);

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/categories";
    }

    @PostMapping("/update_category")
    public String updateCategory(@ModelAttribute ( value = "categories" ) Category category, RedirectAttributes redirectAttributes){
        Message message = categoryService.saveCategory(category);
        String link;

        if (message.getWebclass().equals("alert alert-success")) {
            link ="redirect:/categories";
        } else
            link = "redirect:/category/" + category.getId();

        redirectAttributes.addFlashAttribute("message", message);
        return link;
    }
}
