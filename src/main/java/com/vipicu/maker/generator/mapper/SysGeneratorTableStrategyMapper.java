package com.vipicu.maker.generator.mapper;

import com.vipicu.maker.generator.core.mapper.CrudMapper;
import com.vipicu.maker.generator.entity.TableStrategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 表生成策略 Mapper 接口
 * </p>
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Mapper
public interface SysGeneratorTableStrategyMapper extends CrudMapper<TableStrategy> {

}
