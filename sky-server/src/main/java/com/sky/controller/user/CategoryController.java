package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //根据
    @GetMapping("/list")
    public Result<List<Category>>  List(Integer type) {
            List<Category> list=categoryService.list(type);
            return Result.success(list);
    }
}
