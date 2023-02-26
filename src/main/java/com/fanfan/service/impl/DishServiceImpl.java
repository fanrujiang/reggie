package com.fanfan.service.impl;

import com.fanfan.bean.PageBean;
import com.fanfan.dto.DishDto;
import com.fanfan.mapper.DishFlavorMapper;
import com.fanfan.mapper.DishMapper;
import com.fanfan.pojo.Dish;
import com.fanfan.pojo.DishFlavor;
import com.fanfan.service.DishFlavorService;
import com.fanfan.service.DishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        DishDto byId =dishMapper.getById(id);
        System.out.println(byId);
        //根据菜品id查询出的口味集合
        ArrayList<DishFlavor> flavors = dishFlavorService.findByDishId(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId,dishDto);
        dishDto.setFlavors(flavors);

        return dishDto;
    }
}
