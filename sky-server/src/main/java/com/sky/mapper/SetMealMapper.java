package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetMealMapper {

    Page<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    void update(Setmeal setmeal);

    @AutoFill(value= OperationType.INSERT)
    void insert(Setmeal setmeal);
}
