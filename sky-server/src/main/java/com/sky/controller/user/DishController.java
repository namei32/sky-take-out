package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    //根据分类id查询菜品
    @GetMapping("/list")
    public Result<List<DishVO>> findDishes(Long categoryId){
        String key="dish_"+categoryId;
        List<DishVO>dishVOS=(List<DishVO>)redisTemplate.opsForValue().get(key);
        if(dishVOS!=null&&dishVOS.size()>0){
            return Result.success(dishVOS);
        }
        Dish dish=new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        dishVOS=dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key,dishVOS);
        return Result.success(dishVOS);

    }
}
