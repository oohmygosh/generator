package com.vipicu.maker.generator.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式判断工具类
 *
 * @author oohmygosh
 * @since 2022-03-18
 */
public class RegexUtils {

    /**
     * 电子邮箱
     */
    public static String EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public static boolean isEmail(String email) {
        return matches(EMAIL, email);
    }

    /**
     * 判断是否为密码为4~20位数字,英文,符号至少两种组合的字符
     *
     * @param password 密码
     * @return boolean
     */
    public static boolean isPassword(String password) {
        return isAlphanumericSymbols(password, 6, 20);
    }

    /**
     * 判断数字,英文,符号至少两种组合的字符
     *
     * @param input 待判断字符串
     * @param min   最小长度
     * @param max   最大长度
     * @return boolean
     */
    public static boolean isAlphanumericSymbols(CharSequence input, int min, int max) {
        String pwd = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?![,\\.!#%'\\+\\*\\-:;^_`]+$)[,\\.!#%'\\+\\*\\-:;^_`0-9A-Za-z]{" +
                min + "," + max + "}$";
        return matches(pwd, input);
    }

    /**
     * 包路径验证
     *
     * @param input 输入
     * @return boolean
     */
    public static boolean isPackagePath(CharSequence input) {
        return matches("[a-zA-Z]+[0-9a-zA-Z_]*(\\.[a-zA-Z]+[0-9a-zA-Z_]*)*\\.[a-zA-Z]+[0-9a-zA-Z_]*($[a-zA-Z]+[0-9a-zA-Z_]*)*", input);
    }

    /**
     * 字符串正则判断
     *
     * @param regex 正则
     * @param input 待判断字符串
     * @return boolean
     */
    public static boolean matches(String regex, CharSequence input) {
        return Pattern.matches(regex, input);
    }
}
