package com.codegym.service.blog;

import com.codegym.model.blog.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IBlogService {

    Page<Blog> findAllBlog(Pageable pageable);

    Optional<Blog> findById(Long id);

    void save(Blog blog);

    void remove(Long id);

    Iterable<Blog> findAllByCategory(Category category);
}
