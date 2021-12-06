package com.codegym.controller.rest;

import com.codegym.model.Category;
import com.codegym.model.blog.Blog;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category/rest")
public class CategoryRestController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/home")
    public ResponseEntity<Iterable<Category>> findAll() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Category> findCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }
////    produces = MediaType.APPLICATION_JSON_VALUE
    @PostMapping(value = "/create")
    public ResponseEntity<Category> create(@RequestBody Category category){
        categoryService.save(category);
        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,@RequestBody Category category){
        Optional<Category> categoryOptional=categoryService.findById(id);
        category.setId(categoryOptional.get().getId());
        categoryService.save(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Blog> delete(@PathVariable Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
