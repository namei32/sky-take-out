package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingMapper shoppingMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;
  public void add(ShoppingCartDTO shoppingCartDTO) {
      ShoppingCart shoppingCart = new ShoppingCart();
      BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
      Long userId = BaseContext.getCurrentId();
      shoppingCart.setUserId(userId);
      List<ShoppingCart> list = shoppingMapper.list(shoppingCart);
      if (list.size() > 0 && list != null) {
          ShoppingCart cart = list.get(0);
          cart.setNumber(cart.getNumber() + 1);
          shoppingMapper.update(cart);
      } else {


          Long dishId = shoppingCartDTO.getDishId();
          Long setmealId = shoppingCartDTO.getSetmealId();
          if (dishId != null) {
              Dish dish = dishMapper.getById(dishId);
              shoppingCart.setName(dish.getName());
              shoppingCart.setImage(dish.getImage());
              shoppingCart.setAmount(dish.getPrice());


          } else {
              Setmeal setmeal = setMealMapper.getById(setmealId);
              shoppingCart.setName(setmeal.getName());
              shoppingCart.setImage(setmeal.getImage());
              shoppingCart.setAmount(setmeal.getPrice());

          }
          shoppingCart.setNumber(1);
          shoppingCart.setCreateTime(LocalDateTime.now());
          shoppingMapper.insert(shoppingCart);
      }
  }
    public List<ShoppingCart> showShoppingCart(){
          Long userId = BaseContext.getCurrentId();
          List<ShoppingCart>list= shoppingMapper.showShoppingCart(userId);
          return list;
    }

    @Override
    public void deleteShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingMapper.clean(userId);
    }


}
