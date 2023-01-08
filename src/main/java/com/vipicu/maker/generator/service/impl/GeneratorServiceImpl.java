package com.vipicu.maker.generator.service.impl;

import com.vipicu.maker.generator.core.bean.BeanConvert;
import com.vipicu.maker.generator.core.mapper.CrudMapper;
import com.vipicu.maker.generator.core.service.BaseServiceImpl;
import com.vipicu.maker.generator.entity.SysGeneratorDb;
import com.vipicu.maker.generator.entity.TableBase;
import com.vipicu.maker.generator.jdbc.DatabaseMetaDataWrapper;
import com.vipicu.maker.generator.jdbc.update.DbUpdateDecorator;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 服务impl
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
public class GeneratorServiceImpl<M extends CrudMapper<T>, T extends TableBase> extends BaseServiceImpl<M, T> {

    /**
     * 保存或更新数据库
     *
     * @param db        数据库实例
     * @param function  数据库查询函数
     * @param queryList 查询列表
     * @return {@link List}<{@link T}>
     */
    @SneakyThrows
    public <O extends BeanConvert> List<T> saveOrUpdateByDb(SysGeneratorDb db, Function<DatabaseMetaDataWrapper, Map<String, O>> function, List<T> queryList, Consumer<T> fill) {
        try (Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
            DatabaseMetaDataWrapper dataWrapper = new DatabaseMetaDataWrapper(conn);
            Map<String, O> dbResult = function.apply(dataWrapper);
            int size = dbResult.size();
            ArrayList<Long> removeList = new ArrayList<>();
            List<T> result = queryList.stream().filter(item -> {
                if (dbResult.containsKey(item.getName())) {
                    item.copyProperty(dbResult.get(item.getName()));
                    dbResult.remove(item.getName());
                    return true;
                } else {
                    removeList.add(item.getId());
                    return false;
                }
            }).collect(Collectors.toList());
            if (!removeList.isEmpty())
                super.removeBatchByIds(removeList);
            if (result.size() != size) {
                dbResult.forEach((k, v) -> {
                    Class<T> aClass;
                    try {
                        //noinspection unchecked
                        aClass = (Class<T>) Class.forName(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Bean加载异常!!");
                    }
                    T convert = v.convert(aClass);
                    convert.setName(k);
                    if (fill != null)
                        fill.accept(convert);
                    result.add(convert);
                });
                super.saveOrUpdateBatch(result);
            }
            return result;
        }
    }

    /**
     * 执行sql
     *
     * @param consumers  消费者
     * @param connection 连接
     */
    @SafeVarargs
    public final void executeSql(Connection connection, @NotNull Consumer<DbUpdateDecorator>... consumers) {
        DbUpdateDecorator dataBaseUpdate = new DbUpdateDecorator(connection);
        for (Consumer<DbUpdateDecorator> consumer : consumers) {
            consumer.accept(dataBaseUpdate);
        }
    }
}
