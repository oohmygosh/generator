package com.vipicu.maker.generator.engine;

import com.apifan.common.random.entity.DataField;
import com.apifan.common.random.util.DataUtils;
import com.apifan.common.random.util.JsonUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.entity.*;
import com.vipicu.maker.generator.entity.po.RenderResult;
import com.vipicu.maker.generator.entity.po.TableField;
import com.vipicu.maker.generator.entity.po.TableInfo;
import com.vipicu.maker.generator.enums.DateType;
import com.vipicu.maker.generator.enums.NamingStrategy;
import com.vipicu.maker.generator.enums.RenderType;
import com.vipicu.maker.generator.mock.MockGeneratorRegistry;
import com.vipicu.maker.generator.service.*;
import com.vipicu.maker.generator.utils.SpringUtils;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 全局配置
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
@Getter
public class GlobalGeneratorConfig {

    /**
     * 日期
     */
    private final Supplier<String> commentDate = () -> new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    /**
     * 需要生成的表
     */
    private List<TableInfo> tables;
    /**
     * 全局战略
     */
    private SysGeneratorStrategy globalStrategy;

    private GlobalGeneratorConfig() {
    }

    public static class Builder {
        private final GlobalGeneratorConfig generatorGlobalConfig;
        private final ISysGeneratorSuperclassService superclassService;
        private final ISysGeneratorFieldService fieldService;
        private final ISysGeneratorTemplateService templateService;
        private final ISysGeneratorTableStrategyService tableStrategyService;
        private final ISysGeneratorStrategyService strategyService;

        public Builder() {
            this.generatorGlobalConfig = new GlobalGeneratorConfig();
            this.superclassService = SpringUtils.getBean(ISysGeneratorSuperclassService.class);
            this.fieldService = SpringUtils.getBean(ISysGeneratorFieldService.class);
            this.templateService = SpringUtils.getBean(ISysGeneratorTemplateService.class);
            this.tableStrategyService = SpringUtils.getBean(ISysGeneratorTableStrategyService.class);
            this.strategyService = SpringUtils.getBean(ISysGeneratorStrategyService.class);
        }

        public Builder tables(List<SysGeneratorTable> tables) {
            this.generatorGlobalConfig.globalStrategy = strategyService.getOne(Wrappers.<SysGeneratorStrategy>lambdaQuery().eq(SysGeneratorStrategy::getPid, tables.get(0).getDbId()));
            ArrayList<TableInfo> tableInfos = new ArrayList<>();
            tables.forEach(table -> {
                table.setTableStrategy(tableStrategyService.getOne(Wrappers.<TableStrategy>lambdaQuery().eq(TableStrategy::getTableId, table.getId())));
                table.setFields(fieldService.fetchTableField(table.getId()));
                TableStrategy tableStrategy = Optional.ofNullable(table.getTableStrategy()).orElseGet(() -> {
                    TableStrategy strategy = TableStrategy.getDefaultStrategy(table.getId());
                    tableStrategyService.save(strategy);
                    return strategy;
                });
                List<SysGeneratorTemplate> templates = Optional.ofNullable(tableStrategy.getTemplates()).map(templateService::listByIds).orElse(null);
                EnumMap<RenderType, String> classTypeStringEnumMap = new EnumMap<>(RenderType.class);
                Optional.ofNullable(templates).ifPresent(item -> item.forEach(template -> classTypeStringEnumMap.put(template.getType(), template.getTemplate())));
                ApiAssert.fail(classTypeStringEnumMap.isEmpty(), "请选择生成模板");
                TreeSet<String> commonField = new TreeSet<>();
                TreeSet<String> importPackages = new TreeSet<>();
                ArrayList<TableInfo.SuperClass> superClasses = new ArrayList<>();
                // 父类配置
                List<SysGeneratorSuperclass> superclasses = Optional.ofNullable(tableStrategy.getSuperclass())
                        .map(superclassService::listByIds).orElse(null);
                Optional.ofNullable(superclasses).ifPresent(list -> list.forEach(item -> {
                    //noinspection SwitchStatementWithTooFewBranches,AlibabaSwitchStatement
                    switch (item.getType()) {
                        case ENTITY -> {
                            superClasses.add(new TableInfo.SuperClass(item.getName(), item.getPath(), item.getType()));
                            importPackages.add(item.getPath());
                            item.getField().stream().filter(Objects::nonNull).forEach(f -> commonField.add(f.getName()));
                        }
                        default ->
                                superClasses.add(new TableInfo.SuperClass(item.getName(), item.getPath(), item.getType()));
                    }
                }));
                tableInfos.add(TableInfo.builder()
                        .comment(table.getComment())
                        .tablePrefix(generatorGlobalConfig.globalStrategy.getTablePrefix())
                        .tableSuffix(generatorGlobalConfig.globalStrategy.getTableSuffix())
                        .fieldPrefix(tableStrategy.getFieldPrefix())
                        .fieldSuffix(tableStrategy.getFieldSuffix())
                        .validation(tableStrategy.getValidation())
                        .lombok(tableStrategy.getLombok())
                        .dateType(DateType.TIME_PACK)
                        .naming(NamingStrategy.underline_to_camel)
                        .name(table.getName())
                        .superClasses(superClasses)
                        .swagger(tableStrategy.getSwagger())
                        .mapper(tableStrategy.getMapper())
                        .restController(true)
                        .author(tableStrategy.getAuthor())
                        .packagePath(StringUtils.isBlank(tableStrategy.getPackagePath()) ? generatorGlobalConfig.getGlobalStrategy().getPackagePath() : tableStrategy.getPackagePath())
                        .commonFields(commonField)
                        .templateMap(classTypeStringEnumMap)
                        // fields 构建
                        .fields(table.getFields().stream().map(field -> TableField.builder()
                                .jdbcType(field.getJdbcType())
                                .name(field.getName())
                                .length(field.getLength()).primaryKey(field.isPrimaryKey())
                                .comment(field.getComment())
                                .scale(field.getScale())
                                .nullable(field.isNullable())
                                .build()).collect(Collectors.toList()))
                        .importPackages(importPackages)
                        .build());
            });
            this.generatorGlobalConfig.tables = tableInfos;
            return this;
        }

        public Builder mockData(SysGeneratorTable table, Consumer<List<RenderResult>> consumer) {
            List<DataField> dataFields = new ArrayList<>();
            for (SysGeneratorField field : table.getFields()) {
                if (field.getMockType() != null && StringUtils.isNotBlank(field.getMockParam()))
                    dataFields.add(MockGeneratorRegistry.getGenerator(field.getMockType()).doGenerator(field));
            }
            if (!dataFields.isEmpty()) {
                ArrayList<RenderResult> renderResults = new ArrayList<>();
                String json = DataUtils.generateJson(dataFields, table.getTableStrategy().getMockNum());
                StringBuilder insert = new StringBuilder();
                List<Map<String, Object>> maps = JsonUtils.parseMapList(json);
                maps.forEach(map -> insert.append("INSERT INTO ")
                        .append(table.getName()).append("(")
                        .append(String.join(",", map.keySet()))
                        .append(")").append(" values(")
                        .append(map.values().stream().map(Object::toString).map(x -> "'" + x + "'").collect(Collectors.joining(",")))
                        .append(");\n"));
                renderResults.add(new RenderResult(json, "", RenderType.JSON));
                renderResults.add(new RenderResult(insert.toString(), "", RenderType.SQL));
                consumer.accept(renderResults);
            }
            return this;
        }

        public void templateRender(ConsumerAfterRender consumerAfterRender) {
            VelocityTemplate template = new VelocityTemplate(this.generatorGlobalConfig);
            template.batchOutput(consumerAfterRender);
        }

        public GlobalGeneratorConfig build() {
            return this.generatorGlobalConfig;
        }
    }
}
