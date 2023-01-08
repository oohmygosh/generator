package com.vipicu.maker.generator.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 制造商元对象处理程序
 *
 * @author oohmygosh
 * @since 2022/11/28
 */
@Component
public class MakerMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillHasGetter(metaObject, "createId", 0L, false);
        this.fillHasGetter(metaObject, "createBy", "oohmygosh", false);
        this.fillHasGetter(metaObject, "createTime", LocalDateTime.now(), false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillHasGetter(metaObject, "updateBy", "oohmygosh", true);
        this.fillHasGetter(metaObject, "updateTime", LocalDateTime.now(), true);
    }

    protected void fillHasGetter(MetaObject metaObject, String fieldName, Object fieldVal, boolean isCover) {
        if (metaObject.hasGetter(fieldName)) {
            if (isCover)
                this.setFieldValByName(fieldName, fieldVal, metaObject);
            else
                this.fillStrategy(metaObject, fieldName, fieldVal);
        }
    }
}
