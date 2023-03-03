package com.fanfan.service.impl;

import com.fanfan.bean.Dish;
import com.fanfan.bean.DishFlavor;
import com.fanfan.bean.PageBean;
import com.fanfan.common.BaseContext;
import com.fanfan.dto.DishDto;
import com.fanfan.mapper.DishMapper;
import com.fanfan.service.DishFlavorService;
import com.fanfan.service.DishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public PageBean page(int page, int pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        Page<DishDto> p = dishMapper.findPage(name);
        return new PageBean((int) p.getTotal(), p.getResult());
    }

    /**
     * 根据id查询菜品信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getById(Long id) {
        //查询出当前菜品的信息
        DishDto byId = dishMapper.getById(id);
        System.out.println(byId);
        //根据菜品id查询出的口味集合
        ArrayList<DishFlavor> flavors = dishFlavorService.findByDishId(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId, dishDto);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    /**
     * 根据分类查询菜品集合
     *
     * @param categoryId 分类id
     * @return 菜品集合
     */
    @Override
    public ArrayList<Dish> getByCategoryId(Long categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }

    /**
     * 修改菜品的启售停售
     *
     * @param status 状态
     * @param ids    菜品ids
     */
    @Override
    public void status(int status, String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            Dish dish = new Dish();
            dish.setId(Long.valueOf(id));
            dish.setStatus(status);
            dish.setUpdateTime(LocalDateTime.now());
            dish.setUpdateUser(BaseContext.getCurrentId());
            dishMapper.update(dish);
        }
    }

    /**
     * 修改菜品信息
     *
     * @param dishDto 菜品dto
     */
    @Override
    @Transactional
    public void update(DishDto dishDto) {
        //更新dish表中的数据
        dishMapper.update(dishDto);
        //删除当前菜品的所有口味
        dishFlavorService.deleteByDishId(dishDto.getId());

        //向口味表加入口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            //设置菜品id
            flavor.setDishId(dishDto.getId());
            dishFlavorService.insert(flavor);

        }
    }
}
