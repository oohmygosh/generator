package com.vipicu.maker.generator.config;


/**
 * 定义常量
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-31
 */
public interface ConstVal {

    String MODULE_NAME = "ModuleName";
    String ENTITY = "Entity";
    String ENTITY_NAME = "entity";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String XML = "Xml";
    String CONTROLLER = "Controller";
    String REST_CONTROLLER = "restController";
    String SWAGGER = "swagger";
    String PACKAGE = "package";
    String AUTHOR = "author";
    String DATE = "date";
    String LOMBOK = "entityLombokModel";
    String TABLE = "table";
    String FIELDS = "fields";

    String UNDERLINE = "_";

    /**
     * 父类
     */
    String SUPER_CONTROLLER_PKG = "superControllerClassPackage";
    String SUPER_ENTITY_PKG = "superEntityClassPackage";
    String SUPER_MAPPER_PKG = "superMapperClassPackage";
    String SUPER_SERVICE_PKG = "superServiceClassPackage";
    String SUPER_SERVICE_IMPL_PKG = "superServiceImplClassPackage";
    String SUPER_CONTROLLER_CLASS = "superControllerClass";
    String SUPER_ENTITY_CLASS = "superEntityClass";
    String SUPER_MAPPER_CLASS = "superMapperClass";
    String SUPER_SERVICE_CLASS = "superServiceClass";
    String SUPER_SERVICE_IMPL_CLASS = "superServiceImplClass";
    String MAPPER_ANNOTATION = "mapperAnnotation";
}
