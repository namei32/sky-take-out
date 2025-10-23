package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DishFlavorMapper {


    void insertBatch(List<DishFlavor> dishFlavors);

    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteByDishid(long id);

    void deleteByDishids(List<Long> dishIds);

    @Select("select * from dish_flavor where  dish_id=#{id}")
    List<DishFlavor> getById(Long id);
}
