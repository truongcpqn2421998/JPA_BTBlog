package com.codegym.repository;

import com.codegym.model.Blog;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class BlogRepository implements IBlogRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Blog> findAllBlog() {
        TypedQuery<Blog> typedQuery=entityManager.createQuery("select c from  Blog c",Blog.class);
        return typedQuery.getResultList();
    }

    @Override
    public Blog findById(Long id) {
        TypedQuery<Blog> typedQuery=entityManager.createQuery("select b from Blog b where b.id= :id",Blog.class);
        typedQuery.setParameter("id",id);
        return typedQuery.getSingleResult();
    }

    @Override
    public void save(Blog blog) {
        if(blog.getId()!=null){
            entityManager.merge(blog);
        }else {
            entityManager.persist(blog);
        }
    }

    @Override
    public void remove(Long id) {
        Blog blog=findById(id);
        entityManager.remove(blog);
    }
}
