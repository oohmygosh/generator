package com.vipicu.maker.generator.mapper;

import com.vipicu.maker.generator.core.mapper.CrudMapper;
import com.vipicu.maker.generator.entity.SysGeneratorField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>
 * 代码生成表字段 Mapper 接口
 * </p>
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Mapper
public interface SysGeneratorFieldMapper extends CrudMapper<SysGeneratorField> {

    /**
     * 得到数据库
     *
     * @param tableId 表id
     */
    //@Select("SELECT sys_generator_db.url as url,sys_generator_db.username as uername , sys_generator_db.password as password  FROM sys_generator_db join sys_generator_table sgt on sys_generator_db.id = sgt.db_id and sgt.id  = #{tableId}")
    @Select("""
            SELECT sys_generator_db.url,
            sys_generator_db.username ,
            sys_generator_db.password,
            sys_generator_db.db_type "dbType",
            sgt.name
             FROM sys_generator_db join sys_generator_table sgt on sys_generator_db.id = sgt.db_id and sgt.id  = #{tableId}
                        """)
    Map<String, String> getDb(Long tableId);

}
