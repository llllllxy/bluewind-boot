package com.liuxingyu.meco.common.utils.verifity;

/**
 * @author liuxingyu01
 * @date 2021-06-27-18:44
 * @description 密码复杂度校验
 **/
public class ComplexityUtils {

    // 数字
    public static final String REG_NUMBER = ".*\\d+.*";
    // 小写字母
    public static final String REG_UPPERCASE = ".*[A-Z]+.*";
    // 大写字母
    public static final String REG_LOWERCASE = ".*[a-z]+.*";
    // 特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
    public static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    public static boolean checkPasswordRule(String password, String username) {
        // 密码为空及长度大于8位小于32位判断
        if (password == null || password.length() < 8 || password.length() > 32) {
            return false;
        }
        // 定义密码复杂度级别
        int i = 0;
        //  matches(String regex) 告知此字符串是否匹配给定的正则表达式
        if (password.matches(ComplexityUtils.REG_NUMBER)) {
            i++; // 1级复杂度
        }
        if (password.matches(ComplexityUtils.REG_LOWERCASE)) {
            i++; // 2级复杂度
        }
        if (password.matches(ComplexityUtils.REG_UPPERCASE)) {
            i++; // 3级复杂度
        }
        if (password.matches(ComplexityUtils.REG_SYMBOL)) {
            i++; // 4级复杂度
        }

        boolean contains = password.contains(username);
        // 复杂度最少为3级
        if (i < 3 || contains) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        boolean flag = checkPasswordRule("wenb1su423!2A", "wenbsu");
        System.out.println(flag);
    }


}
