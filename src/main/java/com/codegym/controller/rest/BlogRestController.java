package com.codegym.controller.rest;

import com.codegym.model.Category;
import com.codegym.model.blog.Blog;
import com.codegym.repository.IBlogRepository;
import com.codegym.service.blog.IBlogService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog/rest")
public class BlogRestController {

    @Autowired
    private IBlogService blogService;



    @GetMapping("/home")
    public ResponseEntity<Iterable<Blog>> findAll() {
        List<Blog> blogs = (List<Blog>) blogService.findAll();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Blog> findBlog(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        return new ResponseEntity<>(blog.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> create(@RequestBody Blog blog){
        blogService.save(blog);
        return new ResponseEntity<>(blog,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Blog> update(@PathVariable Long id,@RequestBody Blog blog){
        Optional<Blog> blogOptional=blogService.findById(id);
        blog.setId(blogOptional.get().getId());
        blogService.save(blog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Blog> delete(@PathVariable Long id){
        blogService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @GetMapping("/findByCategory/{id}")
//    public ResponseEntity<Iterable<Blog>> findBlogByCategory(@PathVariable Long id){
//        Optional<Category> category=categoryService.findById(id);
//        List<Blog> blogs=(List<Blog>) blogService.findAllByCategory(category.get());
//        return new ResponseEntity<>(blogs,HttpStatus.OK);
//    }
}
