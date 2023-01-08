package com.vipicu.maker.generator.entity.po;

import com.vipicu.maker.generator.core.bean.BeanConvert;
import com.vipicu.maker.generator.enums.DateType;
import com.vipicu.maker.generator.enums.JavaType;
import com.vipicu.maker.generator.enums.NamingStrategy;
import com.vipicu.maker.generator.enums.RenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 表信息
 *
 * @author oohmygosh
 * @since 2022/12/20
 */
@Getter
@Setter
@Builder
public class TableInfo implements BeanConvert {
    /**
     * 表名
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 作者
     */
    @Builder.Default
    private String author = "oohmygosh";

    /**
     * 包路径
     */
    @Builder.Default
    private String packagePath = "com.vipicu.maker.generator";

    /**
     * 导入包
     */
    private Set<String> importPackages;

    /**
     * 命名策略
     */
    @Builder.Default
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 日期类型
     */
    @Builder.Default
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 字段
     */
    private List<TableField> fields;

    /**
     * 表前缀
     */
    private Set<String> tablePrefix;

    /**
     * 表后缀
     */
    private Set<String> tableSuffix;

    /**
     * 字段前缀
     */
    private Set<String> fieldPrefix;

    /**
     * 域后缀
     */
    private Set<String> fieldSuffix;

    /**
     * lombok配置
     */
    private LombokConfig lombok;

    /**
     * 是否开启Swagger
     */
    private Boolean swagger;

    /**
     * 是否开启参数校验
     */
    @Builder.Default
    private Boolean validation = true;

    /**
     * 添加@Mapper
     */
    private Boolean mapper;

    /**
     * 添加@restController
     */
    private Boolean restController;

    /**
     * 父类字段
     */
    private Set<String> commonFields;

    /**
     * 自定义地图
     */
    private Map<String, Object> customMap;

    /**
     * 模板映射
     */
    private EnumMap<RenderType, String> templateMap;

    /**
     * 超类
     */
    private List<SuperClass> superClasses;

    /**
     * 映射器名称
     */
    private String mapperName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务impl名称
     */
    private String serviceImplName;

    /**
     * 控制器名称
     */
    private String controllerName;

    public void addImportPackages(boolean condition, String pkgPath) {
        if (condition)
            this.importPackages.add(pkgPath);
    }

    public void addImportPackages(String pkgPath) {
        addImportPackages(true, pkgPath);
    }

    public void processLombokImport() {
        if (lombok != null) {
            addImportPackages(lombok.getAllArgsConstructor(), JavaType.ALL_ARGS_CONSTRUCTOR.getPkg());
            addImportPackages(lombok.getData(), JavaType.DATA.getPkg());
            addImportPackages(lombok.getBuilder(), JavaType.BUILDER.getPkg());
            addImportPackages(lombok.getEqualsAndHashCode(), JavaType.EQUALS_AND_HASH_CODE.getPkg());
            addImportPackages(lombok.getGetterAndSetter(), JavaType.GETTER.getPkg());
            addImportPackages(lombok.getGetterAndSetter(), JavaType.SETTER.getPkg());
            addImportPackages(lombok.getNoArgsConstructor(), JavaType.NO_ARGS_CONSTRUCTOR.getPkg());
            addImportPackages(lombok.getToString(), JavaType.TO_STRING.getPkg());
        }
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public record SuperClass(String className, String pkg, RenderType classType) {

    }
}
