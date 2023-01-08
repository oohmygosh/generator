package com.vipicu.maker.generator.config.convert;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.vipicu.maker.generator.entity.po.TableField;
import com.vipicu.maker.generator.entity.po.TableInfo;
import com.vipicu.maker.generator.enums.NamingStrategy;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * 名称转换接口类
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
public interface INameConvert {

    /**
     * 实体名称转换
     * 执行实体名称转换
     *
     * @param tableInfo 表信息对象
     * @return {@link String}
     */
    @NotNull
    String entityNameConvert(@NotNull TableInfo tableInfo);

    /**
     * 属性名转换
     * 执行属性名称转换
     *
     * @param field 表字段对象，如果属性表字段命名不一致注意 convert 属性的设置
     * @return {@link String}
     */
    @NotNull
    String propertyNameConvert(@NotNull TableField field);

    /**
     * 默认名称转换接口类
     *
     * @author oohmygosh
     * @since 2023/01/08
     */
    record DefaultNameConvert(TableInfo strategyConfig) implements INameConvert {

        @Override
        public @NotNull String entityNameConvert(@NotNull TableInfo tableInfo) {
            return NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategyConfig.getNaming(), strategyConfig.getTablePrefix(), strategyConfig.getTableSuffix()));
        }

        @Override
        public @NotNull String propertyNameConvert(@NotNull TableField field) {
            return processName(field.getName(), strategyConfig.getNaming(), strategyConfig.getFieldPrefix(), strategyConfig.getFieldSuffix());
        }

        private String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
            String propertyName = name;
            // 删除前缀
            if (prefix != null && prefix.size() > 0) {
                propertyName = NamingStrategy.removePrefix(propertyName, prefix);
            }
            // 删除后缀
            if (suffix != null && suffix.size() > 0) {
                propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
            }
            if (StringUtils.isBlank(propertyName)) {
                throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
            }
            // 下划线转驼峰
            if (NamingStrategy.underline_to_camel.equals(strategy)) {
                return NamingStrategy.underlineToCamel(propertyName);
            }
            return propertyName;
        }
    }
}
