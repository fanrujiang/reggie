package com.fanfan.controller;

import com.fanfan.bean.PageBean;
import com.fanfan.common.R;
import com.fanfan.dto.SetmealDto;
import com.fanfan.service.SetmealService;
import org.springframework.web.bind.annotation.*;

/**
 * 套餐控制器
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    /**
     * 套餐分页
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<PageBean> page(int page, int pageSize, String name) {

        PageBean pageBean = setmealService.page(page, pageSize, name);
        return R.success(pageBean);
    }

    /**
     * 根据id查询一个套餐的所有信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getById(id);
        return R.success(setmealDto);
    }

    /**
     * 根据套餐id删除套餐
     * @param ids 套餐ids
     */
    @DeleteMapping
    public R<String> deleteByIds(String ids){
        setmealService.deleteByIds(ids);
        return R.success("删除成功");
    }
}
