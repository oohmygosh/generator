package com.vipicu.maker.generator.mapper;

import com.vipicu.maker.generator.core.mapper.CrudMapper;
import com.vipicu.maker.generator.entity.SysGeneratorDb;
import com.vipicu.maker.generator.entity.SysGeneratorTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 代码生成表资源 Mapper 接口
 * </p>
 *
 * @author oohmygosh
 * @since 2022-12-16
 */
@Mapper
public interface SysGeneratorTableMapper extends CrudMapper<SysGeneratorTable> {

    /**
     * 获取数据库表id
     *
     * @param tableId 表id
     * @return {@link SysGeneratorDb}
     */
    @Select("select * from sys_generator_db where id = (select db_id from sys_generator_table where sys_generator_table.id = #{tableId})")
    SysGeneratorDb getDbByTableId(Long tableId);

}
