package com.vipicu.maker.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.vipicu.maker.generator.config.ConstVal;
import com.vipicu.maker.generator.config.convert.INameConvert;
import com.vipicu.maker.generator.entity.SysGeneratorStrategy;
import com.vipicu.maker.generator.entity.po.RenderResult;
import com.vipicu.maker.generator.entity.po.TableField;
import com.vipicu.maker.generator.entity.po.TableInfo;
import com.vipicu.maker.generator.enums.IColumnType;
import com.vipicu.maker.generator.enums.RenderType;
import com.vipicu.maker.generator.type.TypeRegistry;

import java.util.*;

/**
 * 抽象模板引擎
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
public abstract class AbstractTemplateEngine {
    protected final GlobalGeneratorConfig globalGeneratorConfig;

    public AbstractTemplateEngine(GlobalGeneratorConfig globalGeneratorConfig) {
        this.globalGeneratorConfig = globalGeneratorConfig;
    }

    public Map<String, Object> getObjectMap(TableInfo table) {
        HashMap<String, Object> objectMap = new HashMap<>(16);
        SysGeneratorStrategy globalStrategy = Optional.ofNullable(globalGeneratorConfig.getGlobalStrategy()).orElse(new SysGeneratorStrategy());
        Map<String, String> packageInfo = new HashMap<>(8);
        String parent = StringUtils.isNotBlank(table.getPackagePath()) ? table.getPackagePath() : globalStrategy.getPackagePath();
        packageInfo.put(ConstVal.ENTITY, StringUtils.isBlank(parent) ? "entity" : (parent + StringPool.DOT + "entity"));
        packageInfo.put(ConstVal.MAPPER, StringUtils.isBlank(parent) ? "mapper" : (parent + StringPool.DOT + "mapper"));
        packageInfo.put(ConstVal.SERVICE, StringUtils.isBlank(parent) ? "service" : (parent + StringPool.DOT + "service"));
        packageInfo.put(ConstVal.SERVICE_IMPL, StringUtils.isBlank(parent) ? "serviceImpl" : (parent + StringPool.DOT + "serviceImpl"));
        packageInfo.put(ConstVal.CONTROLLER, StringUtils.isBlank(parent) ? "controller" : (parent + StringPool.DOT + "controller"));
        packageInfo.put(ConstVal.MODULE_NAME, parent);
        objectMap.put(ConstVal.REST_CONTROLLER, true);
        for (TableInfo.SuperClass superClass : table.getSuperClasses()) {
            //noinspection AlibabaSwitchStatement
            switch (superClass.classType()) {
                case SERVICE -> {
                    objectMap.put(ConstVal.SUPER_SERVICE_CLASS, superClass.className());
                    objectMap.put(ConstVal.SUPER_SERVICE_PKG, superClass.pkg());
                }
                case ENTITY -> {
                    objectMap.put(ConstVal.SUPER_ENTITY_CLASS, superClass.className());
                    objectMap.put(ConstVal.SUPER_ENTITY_PKG, superClass.pkg());
                }
                case CONTROLLER -> {
                    objectMap.put(ConstVal.SUPER_CONTROLLER_CLASS, superClass.className());
                    objectMap.put(ConstVal.SUPER_CONTROLLER_PKG, superClass.pkg());
                }
                case MAPPER -> {
                    objectMap.put(ConstVal.SUPER_MAPPER_CLASS, superClass.className());
                    objectMap.put(ConstVal.SUPER_MAPPER_PKG, superClass.pkg());
                }
                case SERVICE_IMPL -> {
                    objectMap.put(ConstVal.SUPER_SERVICE_IMPL_CLASS, superClass.className());
                    objectMap.put(ConstVal.SUPER_SERVICE_IMPL_PKG, superClass.pkg());
                }
                default -> {
                }
            }
        }
        objectMap.put(ConstVal.MAPPER_ANNOTATION, table.getMapper());
        objectMap.put(ConstVal.SWAGGER, table.getSwagger());
        objectMap.put(ConstVal.PACKAGE, packageInfo);
        objectMap.put(ConstVal.AUTHOR, StringUtils.isNotBlank(table.getAuthor()) ? table.getAuthor() : globalStrategy.getAuthor());
        objectMap.put(ConstVal.DATE, globalGeneratorConfig.getCommentDate().get());
        objectMap.put(ConstVal.LOMBOK, table.getLombok());
        return objectMap;
    }

    public void batchOutput(ConsumerAfterRender consumerAfterRender) {
        ArrayList<RenderResult> renderResults = new ArrayList<>();
        globalGeneratorConfig.getTables().forEach(table -> {
            Map<String, Object> objectMap = getObjectMap(table);
            // 载入自定义字段
            if (table.getCustomMap() != null) {
                objectMap.putAll(table.getCustomMap());
            }
            Map<?, ?> pkg;
            if (objectMap.get(ConstVal.PACKAGE) instanceof Map<?, ?> pkgMap)
                pkg = pkgMap;
            else
                pkg = Collections.EMPTY_MAP;
            // 渲染Entity
            Optional.ofNullable(renderEntity(objectMap, table)).map(result -> renderResults.add(new RenderResult(result, Optional.ofNullable(pkg.get(ConstVal.ENTITY)).map(x -> x + StringPool.DOT).orElse(null) + objectMap.get(ConstVal.ENTITY_NAME), RenderType.ENTITY)));
            // 渲染Mapper
            Optional.ofNullable(renderMapper(objectMap, table)).map(result -> renderResults.add(new RenderResult(result, Optional.ofNullable(pkg.get(ConstVal.MAPPER)).map(x -> x + StringPool.DOT).orElse(null) + table.getMapperName(), RenderType.MAPPER)));
            // 渲染Service
            Optional.ofNullable(renderService(objectMap, table)).map(result -> renderResults.add(new RenderResult(result, Optional.ofNullable(pkg.get(ConstVal.SERVICE)).map(x -> x + StringPool.DOT).orElse(null) + table.getServiceName(), RenderType.SERVICE)));
            // 渲染ServiceImpl
            Optional.ofNullable(renderServiceImpl(objectMap, table)).map(result -> renderResults.add(new RenderResult(result, Optional.ofNullable(pkg.get(ConstVal.SERVICE_IMPL)).map(x -> x + StringPool.DOT).orElse(null) + table.getServiceImplName(), RenderType.SERVICE_IMPL)));
            // 渲染Controller
            Optional.ofNullable(renderController(objectMap, table)).map(result -> renderResults.add(new RenderResult(result, Optional.ofNullable(pkg.get(ConstVal.CONTROLLER)).map(x -> x + StringPool.DOT).orElse(null) + table.getControllerName(), RenderType.CONTROLLER)));
            // 渲染TypeScript
            Optional.ofNullable(renderTypescript(objectMap, table)).map(result -> renderResults.add(new RenderResult(result, ConstVal.ENTITY_NAME + ".ts", RenderType.TS)));
        });
        consumerAfterRender.consumer(renderResults);

    }

    public String writer(Map<String, Object> objectMap, String template) {
        return null;
    }


    protected String renderEntity(Map<String, Object> objectMap, TableInfo table) {
        table.processLombokImport();
        objectMap.put(ConstVal.TABLE, table);
        INameConvert nameConvert = new INameConvert.DefaultNameConvert(table);
        TypeRegistry typeRegistry = new TypeRegistry(table);
        // 设置属性名 & 类型
        List<TableField> fields = table.getFields();
        fields.forEach(f -> {
            f.setPropertyName(nameConvert.propertyNameConvert(f));
            IColumnType columnType = typeRegistry.getColumnType(f);
            f.setPropertyType(columnType.getType());
            f.setCapitalName(f.getPropertyName().substring(0, 1).toUpperCase() + f.getPropertyName().substring(1));
            if (columnType.getPkg() != null)
                table.addImportPackages(columnType.getPkg());
        });
        fields.removeIf(f -> table.getCommonFields().contains(f.getPropertyName()));
        objectMap.put(ConstVal.ENTITY_NAME, nameConvert.entityNameConvert(table));
        objectMap.put(ConstVal.FIELDS, table.getFields());
        return writer(objectMap, table.getTemplateMap().get(RenderType.ENTITY));
    }

    protected String renderMapper(Map<String, Object> objectMap, TableInfo table) {
        table.setMapperName(objectMap.get(ConstVal.ENTITY_NAME) + ConstVal.MAPPER);
        return writer(objectMap, table.getTemplateMap().get(RenderType.MAPPER));
    }

    protected String renderService(Map<String, Object> objectMap, TableInfo table) {
        table.setServiceName(objectMap.get(ConstVal.ENTITY_NAME) + ConstVal.SERVICE);
        return writer(objectMap, table.getTemplateMap().get(RenderType.SERVICE));
    }


    protected String renderServiceImpl(Map<String, Object> objectMap, TableInfo table) {
        table.setServiceImplName(objectMap.get(ConstVal.ENTITY_NAME) + ConstVal.SERVICE_IMPL);
        return writer(objectMap, table.getTemplateMap().get(RenderType.SERVICE_IMPL));

    }

    protected String renderController(Map<String, Object> objectMap, TableInfo table) {
        table.setControllerName(objectMap.get(ConstVal.ENTITY_NAME) + ConstVal.CONTROLLER);
        return writer(objectMap, table.getTemplateMap().get(RenderType.CONTROLLER));
    }

    protected String renderTypescript(Map<String, Object> objectMap, TableInfo table) {
        table.getFields().forEach(f -> {
            switch (f.getPropertyType()) {
                case "String","byte[]" -> f.setPropertyType("string");
                case "Integer","Long","Double","BigDecimal","Short" -> f.setPropertyType("number");
                case "Date","LocalDateTime","LocalDate","Time" -> f.setPropertyType("Date");
                default -> f.setPropertyType("any");
            }
        });
        return writer(objectMap, table.getTemplateMap().get(RenderType.TS));
    }

}
