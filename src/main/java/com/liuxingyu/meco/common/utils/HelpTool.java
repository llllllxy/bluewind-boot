package com.liuxingyu.meco.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class HelpTool {
    public static final BigDecimal ZERO = new BigDecimal(0);


    /**
     * List 转化成特定 String 列如 sql 语句中 in('','') 字符串
     *
     * @param argSplitChar '
     * @param compart      ,
     * @return
     */
    public static String listToSpString(List<?> list, String argSplitChar,
                                        String compart) {
        if (list == null || list.size() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        if (argSplitChar == null)
            argSplitChar = "";
        if (compart == null)
            compart = ",";
        sb.append(argSplitChar).append(String.valueOf(list.get(0))).append(
                argSplitChar);
        for (int i = 1; i < list.size(); i++) {
            sb.append(compart).append(argSplitChar).append(
                    String.valueOf(list.get(i))).append(argSplitChar);
        }
        return sb.toString();
    }

    /**
     * Collection 转化成特定 String 列如 sql 语句中 in('','') 字符串
     *
     * @param col
     * @param argSplitChar '
     * @param compart      ,
     * @return
     */
    public static String CollectionToSpString(Collection<?> col,
                                              String argSplitChar, String compart) {
        if (col == null || col.size() == 0)
            return "";
        Iterator<?> it = col.iterator();
        List<Object> list = new ArrayList<Object>();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return listToSpString(list, argSplitChar, compart);
    }

    /**
     * 数组转化成特定 String 列如 sql 语句中 in('','') 字符串
     *
     * @param argStr
     * @param argSplitChar '
     * @param compart      ,
     * @return
     */
    public static String arrayToSpString(String[] argStr, String argSplitChar,
                                         String compart) {
        return listToSpString(Arrays.asList(argStr), argSplitChar, compart);
    }


    public static String setScale(Object o) {
        return setScale(o, 2);
    }

    public static String setScale(Object o, int precision) {
        BigDecimal temp = null;
        if (o == null) {
            temp = ZERO;
        } else if (o instanceof BigDecimal) {
            temp = (BigDecimal) o;
        } else {
            try {
                temp = new BigDecimal(o.toString());
            } catch (Throwable t) {
                return String.valueOf(o);
            }
        }
        try {
            temp = temp.setScale(precision, BigDecimal.ROUND_HALF_UP);
        } catch (Throwable t) {
        }
        return String.valueOf(temp);
    }

    public static String trimnull(Object o) {
        if (o == null || "null".equals(String.valueOf(o))) {
            return "";
        }
        if (o instanceof String) {
            return (String) o;
        }
        return String.valueOf(o);
    }

    public static String trimzero(Object o) {
        if (o == null)
            return "0";
        String s = (o instanceof String) ? (String) o : o.toString();
        int idx = s.lastIndexOf(".");
        if (idx == -1) {
            return s;
        }
        int pos;
        for (pos = s.length() - 1; pos >= 0 && pos >= idx
                && (s.charAt(pos) == '0' || s.charAt(pos) == '.'); pos--)
            ;
        if (pos == -1) {
            return "0";
        } else {
            return s.substring(0, pos + 1);
        }
    }


    /**
     * 注意：要求list里第一个map的数据必须是全的。
     * 将传入的list中，map值为BigDecimal、Integer值都格式化，加上千分位。
     * 值为0的换为空字符串“”。map中的BigDecimal、Integer值都转换为String类型。
     *
     * @param list
     * @return
     */
    public static List formatDecimal(List list) {

        int lengh = list.size();

        List newlist = new ArrayList();

        for (int i = 0; i < lengh; i++) {
            //数据项键值
            Object[] a = ((Map) list.get(i)).keySet().toArray();

            Map map = (Map) list.get(i);
            Map newMap = new HashMap();
            for (int j = 0; j < a.length; j++) {
                Object p = map.get(a[j]);

                if (p != null) {

                    if (p.getClass().equals(BigDecimal.class)) {
                        p = formatDecimal((BigDecimal) p);
                    } else if (p.getClass().equals(Integer.class)) {
                        p = formatInteger((Integer) p);
                    } else if (p.getClass().equals(String.class)) {
                        p = trimRight((String) p);
                    }

                } else {
                    p = "";
                }

                newMap.put(a[j], p);
            }
            newlist.add(newMap);
        }

        return newlist;
    }

    /**
     * 把传入字符串右面的空格去掉。
     * 加千分位的方法使用。
     *
     * @param a
     * @return
     */
    public static String trimRight(String a) {
        if ("".equals(a)) {
            return a;
        }
        int len = 0;
        for (int i = a.length() - 1; i >= 0; i--) {
            if (a.charAt(i) == ' ') {
                len++;
            } else {
                break;
            }
        }
        return a.substring(0, a.length() - len);
    }


    /**
     * BigDecimal值格式化，加上千分位，0转换为“”。
     *
     * @param b
     * @return
     */
    public static String formatDecimal(BigDecimal b) {
        String a = null;
        if (b == null || b.doubleValue() == 0) {
            a = "";
        } else {
            StringBuffer c = new StringBuffer("#,##0");
            if (b.scale() > 0) {
                c.append(".");
            }
            for (int i = 0; i < b.scale(); i++) {
                c.append("0");
            }
            DecimalFormat nf = new DecimalFormat(c.toString());
            a = nf.format(b.doubleValue());
        }
        return a;
    }

    /**
     * BigDecimal值格式化，加上千分位，0转换为“0.00”。
     *
     * @param b
     * @return
     */
    public static String formatDecimal0(BigDecimal b) {
        String a = null;
        if (b == null || b.doubleValue() == 0) {
            a = "0.00";
        } else {
            StringBuffer c = new StringBuffer("#,##0");
            if (b.scale() > 0) {
                c.append(".");
            }
            for (int i = 0; i < b.scale(); i++) {
                c.append("0");
            }
            DecimalFormat nf = new DecimalFormat(c.toString());
            a = nf.format(b.doubleValue());
        }
        return a;
    }


    /**
     * Integer值格式化，加入千分位，0转换为“”。
     *
     * @param b
     * @return
     */
    public static String formatInteger(Integer b) {
        String a = null;
        if (b == null || b.doubleValue() == 0) {
            a = "";
        } else {
            StringBuffer c = new StringBuffer("#,###");
            DecimalFormat nf = new DecimalFormat(c.toString());
            a = nf.format(b.doubleValue());
        }
        return a;
    }


    /**
     * 在得用MappingTool给bean赋值的时候，需要将String参数转换成 int 或 BigDecimal 型的参数
     * 由于经常出现NumberFormatException错误， 所以在此提供转换方法，返回默认值或0
     *
     * @param argStr
     * @return
     */
    public static int toInt(String argStr) {
        return toInt(argStr, 0);
    }

    public static int toInt(String argStr, int defaultValue) {
        int num = 0;
        if (argStr == null || argStr.trim().equalsIgnoreCase(""))
            return 0;
        try {
            num = Integer.parseInt(argStr);
        } catch (Exception e) {
            num = defaultValue;
        }
        return num;
    }

    /**
     * 转换成BigDecimal，默认值是0
     *
     * @param argStr
     * @return
     */
    public static BigDecimal toBigDecimal(String argStr) {
        return toBigDecimal(argStr, ZERO);
    }

    public static BigDecimal toBigDecimal(String argStr, BigDecimal defaultValue) {
        BigDecimal bd = null;
        if (argStr == null || "".equals(argStr))
            return defaultValue != null ? defaultValue : ZERO;
        try {
            bd = new BigDecimal(argStr);
        } catch (Exception e) {
            bd = defaultValue;
        }
        return bd;
    }


    /**
     * 由Map返回一个key的一个int数组
     *
     * @param map
     * @return 当 map 中有空字符串时，返回的数组中以 "-1" 代替
     */
    public static int[] toIntArray(Map map) {
        int array[] = {0};
        if (map != null && !map.isEmpty()) {
            Object[] o = map.keySet().toArray();
            array = new int[o.length];
            for (int i = 0; i < o.length; i++) {
                array[i] = Integer
                        .parseInt(String.valueOf(o[i]) != null
                                && !String.valueOf(o[i]).trim()
                                .equalsIgnoreCase("") ? String
                                .valueOf(o[i]) : "-1");
            }
            return array;
        }
        return array;
    }

    /**
     * 由Map返回一个key的一个String数组
     *
     * @param map
     * @return
     */
    public static String[] toStringArray(Map map) {
        String array[] = null;
        if (map != null && !map.isEmpty()) {
            Object[] o = map.keySet().toArray();
            array = new String[o.length];
            for (int i = 0; i < o.length; i++) {
                array[i] = String.valueOf(o[i]);
            }
            return array;
        }
        return new String[0];
    }


    /**
     * 将传过来的 region 数组 转化成 String
     *
     * @param argSplitChar
     * @return
     */
    public static String regionToString(String[] argStr, String argSplitChar) {
        if (argStr == null || argStr.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        if (argSplitChar != null)
            sb.append(argSplitChar).append(String.valueOf(argStr[0])).append(
                    argSplitChar);
        else
            sb.append(String.valueOf(argStr[0]));
        for (int i = 1; i < argStr.length; i++) {
            sb.append(",");
            if (argSplitChar != null)
                sb.append(argSplitChar).append(String.valueOf(argStr[i]))
                        .append(argSplitChar);
            else
                sb.append(String.valueOf(argStr[i]));
        }
        return sb.toString();
    }


    /**
     * 字符串 转换成特定的字符串,参看 方法
     *
     */
    public static String muliStringToSpString(String argStr,
                                              String argSplitChar, String compart) {
        StringTokenizer st = new StringTokenizer(argStr, compart);
        String[] s = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            s[i] = st.nextToken();
            i++;
        }
        return listToSpString(Arrays.asList(s), argSplitChar, compart);
    }


    /**
     * 测试理解以上各方法 *
     *
     */
    public static void main(String[] args) {
        System.out.println(arrayToSpString(new String[]{"a", "b", "c", "d",
                "e"}, "'", ","));
        String s = "1,2";
        System.out.println(muliStringToSpString(s, "*", ","));
    }
}