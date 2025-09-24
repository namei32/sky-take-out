package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/setmeal")
@Slf4j
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody  SetmealDTO setmealDTO){
        setMealService.save(setmealDTO);
        return Result.success();

    }

//    @PutMapping
//    public Result updateMeal(SetmealDTO setmealDTO){
//        setMealService.updateMeal(setmealDTO);
//        return Result.success();
//    }
    @GetMapping("/page")
    public Result getMealList(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult= setMealService.getMealList(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

}
