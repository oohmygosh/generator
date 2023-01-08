package com.vipicu.maker.generator.core.editors;

import java.beans.PropertyEditorSupport;

/**
 * 数字编辑器
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
public class NumberEditor extends PropertyEditorSupport {

    public NumberEditor() {
        // to do nothing
    }

    @Override
    public String getJavaInitializationString() {
        Object var1 = this.getValue();
        return var1 != null ? var1.toString() : "null";
    }
}
