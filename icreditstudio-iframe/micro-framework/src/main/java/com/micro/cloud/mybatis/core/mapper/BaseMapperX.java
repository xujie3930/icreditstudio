package com.micro.cloud.mybatis.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.PageParam;
import com.micro.cloud.mybatis.core.util.MyBatisUtils;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 在 MyBatis Plus 的 BaseMapper 的基础上拓展，提供更多的能力
 *
 * @author roy
 */
public interface BaseMapperX<T> extends BaseMapper<T> {

    /**
     * 分页
     *
     * @param pageParam    分页参数
     * @param queryWrapper 查询
     * @return
     */
    default CommonPage<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
        // MyBatis Plus 查询
        IPage<T> mpPage = MyBatisUtils.buildPage(pageParam);
        this.selectPage(mpPage, queryWrapper);
        // 转换返回
        return CommonPage.restPage(mpPage, pageParam);
    }

    /**
     * 根据条件查询唯一记录
     *
     * @param field 字段
     * @param value 字段值
     * @return 唯一记录
     */
    default T selectOne(String field, Object value) {
        return this.selectOne(new QueryWrapper<T>().eq(field, value));
    }

    /**
     * 根据两个字段确定唯一记录
     *
     * @param field1 字段1
     * @param value1 字段值
     * @param field2 字段2
     * @param value2 字段值
     * @return 唯一记录
     */
    default T selectOne(String field1, Object value1, String field2, Object value2) {
        return this.selectOne(new QueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    /**
     * 根据字段查询总数
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    default Integer selectCount(String field, Object value) {
        return this.selectCount(new QueryWrapper<T>().eq(field, value));
    }

    /**
     * 获取所有记录
     *
     * @return
     */
    default List<T> selectList() {
        return this.selectList(new QueryWrapper<>());
    }

    /**
     * 根据条件获取列表
     *
     * @param field 字段
     * @param value 字段值
     * @return
     */
    default List<T> selectList(String field, Object value) {
        return this.selectList(new QueryWrapper<T>().eq(field, value));
    }
}
