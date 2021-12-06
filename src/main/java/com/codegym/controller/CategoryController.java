package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.blog.IBlogService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

//    @RequestMapping("/view/{id}")
//    public ModelAndView view(@PathVariable Long id){
//        Optional<Category> category=categoryService.findById(id);
//
//    }

    @RequestMapping("/home")
    public ModelAndView showCategoryList(){
        ModelAndView modelAndView=new ModelAndView("/category/show");
        Iterable<Category> categories=categoryService.findAll();
        modelAndView.addObject("categorys",categories);
        return modelAndView;
    }

    @RequestMapping("/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView=new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Category category){
        ModelAndView modelAndView=new ModelAndView("redirect:/category/home");
        categoryService.save(category);
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/category/home";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id){
        Optional<Category> category=categoryService.findById(id);
        ModelAndView modelAndView=new ModelAndView("/category/edit");
        modelAndView.addObject("category",category.get());
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute Category category){
        categoryService.save(category);
        ModelAndView modelAndView=new ModelAndView("redirect:/category");
        return modelAndView;
    }


}
