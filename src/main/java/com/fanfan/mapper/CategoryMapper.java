package com.fanfan.mapper;

import com.fanfan.pojo.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CategoryMapper {
    Page<Category> findPage();

    ArrayList<Category> list(Integer type);

    /**
     * 根据排序查询一个分类
     *
     * @param sort
     * @return
     */
    Category getOneBySort(Integer sort);

    /**
     * 根据名字查询一个分类
     *
     * @param name
     * @return
     */
    Category getOneByName(String name);


    /**
     * 新增分类
     *
     * @param category 分类
     */
    @Insert("insert into category values (null,#{type},#{name},#{sort},#{createTime},#{createTime},#{updateUser},#{updateUser})")
    void add(Category category);

    /**
     * 根据id删除分类
     *
     * @param id 分类id
     */
    void deleteById(Long id);
}
