package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private IBlogService blogService;
    @RequestMapping("/home")
    public ModelAndView home(){
        List<Blog> blogs=blogService.findAllBlog();
        ModelAndView modelAndView=new ModelAndView("/home");
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }

    @RequestMapping("/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView=new ModelAndView("create");
        modelAndView.addObject("blog",new Blog());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Blog blog){
        ModelAndView modelAndView=new ModelAndView("create");
        blogService.save(blog);
        modelAndView.addObject("message","create complete");
        return modelAndView;
    }

    @RequestMapping("/info/{id}")
    public ModelAndView info(@PathVariable Long id){
        Blog blog=blogService.findById(id);
        ModelAndView modelAndView=new ModelAndView("info");
        modelAndView.addObject("blog",blog);
        return modelAndView;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id){
        Blog blog=blogService.findById(id);
        ModelAndView modelAndView=new ModelAndView("edit");
        modelAndView.addObject("blog",blog);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView=new ModelAndView("edit");
        modelAndView.addObject("message","Edit complete");
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        blogService.remove(id);
        redirectAttributes.addFlashAttribute("message","delete complete");
        return "redirect:/home";
    }


}
