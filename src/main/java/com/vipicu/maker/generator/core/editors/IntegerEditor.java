package com.vipicu.maker.generator.core.editors;

/**
 * 整数编辑器
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
public class IntegerEditor extends NumberEditor {

    public IntegerEditor() {
        // to do nothing
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(text == null ? null : Integer.decode(text));
    }
}
