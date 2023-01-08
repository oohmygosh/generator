package com.vipicu.maker.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.vipicu.maker.generator.entity.po.TableField;
import com.vipicu.maker.generator.entity.po.TableInfo;
import com.vipicu.maker.generator.enums.JdbcType;
import org.apache.commons.collections4.MapUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * velocity模板
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
public class VelocityTemplate extends AbstractTemplateEngine {

    private final VelocityEngine velocityEngine;

    public VelocityTemplate(GlobalGeneratorConfig globalGeneratorConfig) {
        super(globalGeneratorConfig);
        velocityEngine = new VelocityEngine();
    }

    @Override
    public String writer(Map<String, Object> objectMap, String template) {
        if (StringUtils.isBlank(template))
            return null;
        StringWriter result = new StringWriter();
        velocityEngine.evaluate(new VelocityContext(objectMap), result, "entity", template);
        return result.toString();
    }

    @Override
    public Map<String, Object> getObjectMap(TableInfo table) {
        if (table.getValidation())validationAnnotations(table);
        return super.getObjectMap(table);
    }

    private void validationAnnotations(TableInfo tableInfo) {
        List<TableField> fields = tableInfo.getFields();
        fields.forEach(tableField -> {
            final String lnt = "\n\t";
            final JdbcType jdbcType = tableField.getJdbcType();
            StringBuilder annotations = new StringBuilder();
            boolean tabLine = false;
            if (!tableField.isNullable()) {
                if (JdbcType.VARCHAR == jdbcType) {
                    annotations.append("@NotBlank(groups = Create.class)");
                    tableInfo.addImportPackages("javax.validation.constraints.NotBlank");
                } else {
                    annotations.append("@NotNull(groups = Create.class)");
                    tableInfo.addImportPackages("javax.validation.constraints.NotNull");
                }
                tabLine = true;
            }
            if (JdbcType.VARCHAR == jdbcType) {
                annotations.append(tabLine ? lnt : "");
                annotations.append("@Size(max = ").append(tableField.getLength()).append(")");
                tableInfo.addImportPackages("javax.validation.constraints.Size");
            } else if (JdbcType.BIGINT == jdbcType || JdbcType.INTEGER == jdbcType || JdbcType.TINYINT == jdbcType || JdbcType.SMALLINT == jdbcType || JdbcType.BIT == jdbcType || JdbcType.FLOAT == jdbcType || JdbcType.DOUBLE == jdbcType || JdbcType.DECIMAL == jdbcType) {
                annotations.append(tabLine ? lnt : "");
                annotations.append("@PositiveOrZero");
                tableInfo.addImportPackages("javax.validation.constraints.PositiveOrZero");
                tableInfo.addImportPackages("com.vipicu.maker.core.validation.Update");
            }
            Map<String, Object> customMap = tableField.getCustomMap();
            if (MapUtils.isEmpty(customMap)) {
                customMap = new HashMap<>(4);
            }
            String va = annotations.toString();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(va)) {
                tableInfo.addImportPackages("com.vipicu.maker.core.validation.Create");
            }
            customMap.put("validationAnnotations", va);
            tableField.setCustomMap(customMap);
        });
    }
}
