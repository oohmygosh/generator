package com.vipicu.maker.generator.core.editors;

/**
 * 双编辑器
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
public class DoubleEditor extends NumberEditor {

    public DoubleEditor() {
        // to do nothing
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(text == null ? null : Double.valueOf(text));
    }
}
