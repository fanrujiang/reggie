package com.fanfan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanfan.bean.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
