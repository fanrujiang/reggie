package com.fanfan.mapper;

import com.fanfan.bean.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface DishFlavorMapper {
    /**
     * 根据菜品id查询口味集合
     * @param dishId
     * @return
     */
    ArrayList<DishFlavor> findByDishId(Long dishId);

    /**
     * 根据菜品id删除口味
     * @param dishId 菜品id
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 添加口味
     * @param flavor 口味对象
     */
    @Insert("insert into dish_flavor (id, dish_id, name, value, create_time, update_time, create_user, update_user) values (#{id},#{dishId},#{name},#{value},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(DishFlavor flavor);
}
