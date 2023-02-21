package com.fanfan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanfan.bean.Dish;
import com.fanfan.bean.DishFlavor;
import com.fanfan.dto.DishDto;
import com.fanfan.mapper.DishMapper;
import com.fanfan.service.DishFlavorService;
import com.fanfan.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public void add(DishDto dishDto) {
        //添加菜品
        int row = getBaseMapper().insert(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());

            dishFlavorService.save(flavor);
        }
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品的基本信息，从dish查询
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        //将dish中的属性复制到DishDto
        BeanUtils.copyProperties(dish, dishDto);
        //从dish_flavor表查询对应的口味信息
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(lqw);
        //将flavors装到dishDto中
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //先更新菜品信息
        this.updateById(dishDto);
        //清理对应的口味信息
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(lqw);

        //添加新提交的口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());

            dishFlavorService.save(flavor);
        }
    }

    /**
     * 停售菜品
     *
     * @param ids
     */
    @Override
    public void offStatus(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            Dish dish = this.getById(id);
            dish.setStatus(0);
            this.updateById(dish);
        }

    }

    /**
     * 启售菜品
     *
     * @param ids
     */
    @Override
    public void onStatus(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            Dish dish = this.getById(id);
            dish.setStatus(1);
            this.updateById(dish);
        }
    }

    /**
     * 删除菜品
     *
     * @param ids
     */
    @Override
    @Transactional
    public void delete(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            this.removeById(id);
            LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
            lqw.eq(DishFlavor::getDishId, id);
            List<DishFlavor> list = dishFlavorService.list(lqw);
            for (DishFlavor dishFlavor : list) {
                Long flavorId = dishFlavor.getId();
                dishFlavorService.removeById(flavorId);
            }
        }
    }
}
