package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class  CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page=categoryMapper.pageQuery(categoryPageQueryDTO);
        long total=page.getTotal();
        List<Category> categoryListList=page.getResult();
        return new PageResult(total,categoryListList);
    }

    public void update(CategoryDTO categoryDTO){
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.update(category);
    }

    public void startOrStop(long id,Integer status){
        Category category=Category.builder().id(id).status(status).build();
        categoryMapper.update(category);
    }

    @AutoFill(value = OperationType.INSERT)
    public  void insert(CategoryDTO categoryDTO){
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.ENABLE);
        categoryMapper.insert(category);
    }

    public  void delete(long id){
        categoryMapper.delete(id);
    }

    public List<Category> list(Integer type){
        List<Category> list=categoryMapper.selectList(type);
        return list;
    }
}
