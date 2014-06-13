package com.engc.jlcollege.support.utils;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 14-2-26.
 * 字符串帮助类
 */
public class StringUtility {
    private StringUtility(){

    }
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 获得字符创文本的个数
     * @param content
     * @param word
     * @param preCount
     * @return
     */
    public static int countWord(String content, String word, int preCount) {
        int count = preCount;
        int index = content.indexOf(word);
        if (index == -1) {
            return count;
        } else {
            count++;
            return countWord(content.substring(index + word.length()), word, count);
        }
    }
    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 根据key得到列表
     *
     * @param json
     * @param key
     * @return
     */

    public static String getJsonByKey(String json, String key) {
        int index = json.indexOf(key + ":");
        int index1 = json.indexOf("]", index);
        String result = "";
        if (index != -1) {
            result = json.substring(key.length() + index + 1, index1 + 1);
        }

        return result;
    }

    /**
     * 根据开始，结束，周期解析拼接服务时间串
     *
     * @param startTime
     * @param endTime
     * @param week
     * @return
     */
    public static String getServiceTimeByTimeWithWeek(String startTime,
                                                      String endTime, String week) {
        String serviceTime = "";
        if (startTime != null && endTime != null && week != null) {
            String serviceWeek = "";
            String[] weekDay = week.split(",");
            for (int i = 0; i < weekDay.length; i++) {
                switch (Integer.parseInt(weekDay[i])) {
                    case 1:
                        serviceWeek += "周一" + ",";
                        break;

                    case 2:
                        serviceWeek += "周二" + ",";
                        break;

                    case 3:

                        serviceWeek += "周三" + ",";
                        break;

                    case 4:
                        serviceWeek += "周四" + ",";
                        break;

                    case 5:
                        serviceWeek += "周五" + ",";
                        break;

                    case 6:
                        serviceWeek += "周六" + ",";
                        break;
                    default:
                        serviceWeek += "周日" + ",";
                        break;
                }
            }

            String timeStr = startTime + "--" + endTime;
            serviceTime = serviceWeek
                    .substring(0, serviceWeek.lastIndexOf(","))
                    + "\t"
                    + timeStr;

        }
        return serviceTime;
    }
}
