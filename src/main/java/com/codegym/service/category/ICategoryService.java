package com.codegym.service.category;

import com.codegym.model.Category;

import java.util.Optional;

public interface ICategoryService {
    Iterable<Category> findAll();
    Optional<Category> findById(Long id);
    void save(Category category);
    void delete(Long id);
}
