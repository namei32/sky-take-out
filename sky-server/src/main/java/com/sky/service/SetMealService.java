package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    void updateMeal(SetmealDTO setmealDTO);


    PageResult getMealList(SetmealPageQueryDTO setmealPageQueryDTO);

    void save(SetmealDTO setmealDTO);

    void startOrStop( Integer status,Long id);

    void deleteByIds(List<Long> ids);



    SetmealVO getByIdWithDish(Long id);

    List<Setmeal> getByCategoryId(Long categoryId);

    List<DishItemVO> getListBySetMealId(Long setmealId);
}
