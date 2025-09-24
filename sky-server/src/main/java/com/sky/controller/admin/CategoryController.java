package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Api(tags="菜品分类接口")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @ApiOperation("分类分页查询")
    @GetMapping("/page")
    public Result pageQuery( CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult  =  categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(long id, @PathVariable Integer status){
        categoryService.startOrStop(id,status);
        return Result.success();
    }

    @PostMapping
    public Result saveCategory(@RequestBody  CategoryDTO categoryDTO){
        categoryService.insert(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteCategory(long id){
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
