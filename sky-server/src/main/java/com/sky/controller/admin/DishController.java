package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/admin/dish")
@Api(tags="菜品相关接口")
@Slf4j
@RestController
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    //新增菜品同时保存口味
    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.saveWithFlavor(dishDTO);
        String key ="dish_"+dishDTO.getId();
        redisTemplate.delete(key);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping()
    public Result deleteByIds(@RequestParam List<Long> ids) {
        log.info("菜品删除", ids);
        dishService.deleteBatch(ids);
        cleanCache("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(Long id, @PathVariable Integer status) {
        dishService.startOrStop(id, status);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> queryById(@PathVariable Long id) {
        DishVO dishVO = dishService.queryById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }

    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    @GetMapping("/list")
    public Result<List<Dish>> List(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

}
