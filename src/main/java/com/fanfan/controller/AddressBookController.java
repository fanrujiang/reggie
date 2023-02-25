package com.fanfan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fanfan.bean.AddressBook;
import com.fanfan.common.BaseContext;
import com.fanfan.common.R;
import com.fanfan.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    /**
     * 查询指定用户的地址
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(id != null, AddressBook::getUserId, id).orderByDesc(AddressBook::getIsDefault);
        List<AddressBook> list = addressBookService.list(lqw);
        return R.success(list);
    }

    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return R.success("新增成功");
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */

    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper<>();
        luw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        luw.set(AddressBook::getIsDefault, 0);
        addressBookService.update(luw);

        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);

        return R.success(addressBook);
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> setDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        if (addressBook != null) {
            return R.success(addressBook);
        }
        else {
            return R.error("查询失败");
        }
    }


    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {
        addressBookService.updateById(addressBook);
        return R.success("地址修改成功");
    }
    @DeleteMapping
    public R<String> delete(Long ids){
        addressBookService.removeById(ids);
        return R.success("删除成功");
    }


}
