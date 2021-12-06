package com.codegym.controller;

import com.codegym.model.blog.Blog;
import com.codegym.model.Category;
import com.codegym.model.blog.BlogForm;
import com.codegym.service.blog.IBlogService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;


    @ModelAttribute("categoryList")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @RequestMapping("/home")
    public ModelAndView home(Pageable pageable) {
        Iterable<Blog> blogs = blogService.findAllBlog(pageable);
        ModelAndView modelAndView = new ModelAndView("/blog/home");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blogForm", new BlogForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createBlog(@ModelAttribute BlogForm blogForm) {
        MultipartFile multipartFile = blogForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(blogForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Blog blog=new Blog(blogForm.getId(),blogForm.getTitle(),blogForm.getContent(),fileName,blogForm.getCategory());
        blogService.save(blog);
        ModelAndView modelAndView=new ModelAndView("/blog/create");
        modelAndView.addObject("message","create complete");
        return modelAndView;
    }

    //
    @RequestMapping("/info/{id}")
    public ModelAndView info(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        Optional<Category> category=categoryService.findById(blog.get().getCategory().getId());
        ModelAndView modelAndView = new ModelAndView("/blog/info");
        modelAndView.addObject("blogInfo", blog.get());
        modelAndView.addObject("categoryName",category.get().getName());
        return modelAndView;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        Blog blog1=blog.get();
        String img=blog.get().getImage();
        BlogForm blogForm= new BlogForm(blog1.getId(),blog1.getTitle(),blog1.getContent(),blog1.getCategory());
        modelAndView.addObject("blogForm", blogForm);
        modelAndView.addObject("img",img);
        return modelAndView;
    }


    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute BlogForm blogForm,@RequestParam String categoryOld){
        MultipartFile multipartFile = blogForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(blogForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(blogForm.getImage().isEmpty()){
            fileName=categoryOld;
        }
        Blog blog=new Blog(blogForm.getId(),blogForm.getTitle(),blogForm.getContent(),fileName,blogForm.getCategory());
        blogService.save(blog);
        ModelAndView modelAndView=new ModelAndView("redirect:/blog/home");
        modelAndView.addObject("message","create complete");
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.remove(id);
        redirectAttributes.addFlashAttribute("message", "delete complete");
        return "redirect:/blog/home";
    }


}
