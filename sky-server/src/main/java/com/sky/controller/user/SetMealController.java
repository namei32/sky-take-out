package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
public class SetMealController {

    DishVO dishVO=new DishVO();
    @Autowired
    DishService dishService;
    @Autowired
    SetMealService setMealService;
    //根据分类id查询套餐
    @GetMapping("/list")
    public Result<List<Setmeal>> list( Long categoryId){
        Setmeal setmeal=new Setmeal();
        setmeal.setCategoryId(categoryId);
        List <Setmeal>list=setMealService.getByCategoryId(categoryId);
        return Result.success(list);
    }
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> listDish(@PathVariable Long id){
        List<DishItemVO> dishVOS=setMealService.getListBySetMealId(id);
        return Result.success(dishVOS);

    }
}
