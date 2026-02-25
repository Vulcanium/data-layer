package com.vulcanium.datalayer.service;

import com.vulcanium.datalayer.model.Category;
import com.vulcanium.datalayer.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getCategoriesByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> getCategoriesByProductName(String productName) {
        return categoryRepository.findByProductsName(productName);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
