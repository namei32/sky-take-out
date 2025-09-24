package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl  implements DishService {
    @Autowired
    DishMapper dishmapper;
    @Autowired
    DishFlavorMapper dishflavormapper;
    @Autowired
    SetMealDishMapper setmealdishmapper;

    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishmapper.insert(dish);
        long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishflavormapper.insertBatch(flavors);
        }
    }

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishmapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> dishList = page.getResult();
        return new PageResult(total, dishList);
    }

    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (long id : ids) {
            Dish dish = dishmapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> setmealIds = setmealdishmapper.getSetMealDishIdByDishId(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }

//        for(long id:ids) {
//            dishmapper.deleteById(id);
//            dishflavormapper.deleteByDishid(id);
//        }
        dishmapper.deleteByIds(ids);
        dishflavormapper.deleteByDishids(ids);
    }

    public void startOrStop(Long id, Integer status) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishmapper.update(dish);

    }

    public DishVO queryById(Long id) {
        Dish dish = dishmapper.getById(id);
        List<DishFlavor> dishFlavors = dishflavormapper.getById(dish.getId());
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;

    }

    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishmapper.update(dish);
        dishflavormapper.deleteByDishid(dish.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            dishflavormapper.insertBatch(flavors);
        }
    }
}
