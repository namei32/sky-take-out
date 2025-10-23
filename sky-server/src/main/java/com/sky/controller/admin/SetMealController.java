package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    public Result updateMeal(@RequestBody  SetmealDTO setmealDTO){
        setMealService.updateMeal(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    public Result<PageResult> getMealList(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult= setMealService.getMealList(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        setMealService.startOrStop(status,id);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteByids(@RequestParam List<Long> ids){
        log.info("套餐删除",ids);
        setMealService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setMealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }


}
