package com.vipicu.maker.generator.core.editors;

/**
 * 长时间编辑器
 *
 * @author oohmygosh
 * @since 2022/12/06
 */
public class LongEditor extends NumberEditor {

    public LongEditor() {
        // to do nothing
    }

    @Override
    public String getJavaInitializationString() {
        Object var1 = this.getValue();
        return var1 != null ? var1 + "L" : "null";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(text == null ? null : Long.decode(text));
    }
}
