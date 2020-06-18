package com.qf.hotel.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author ChenJie
 * @date 2020-05-14 19:21:00
 * 功能说明:汉字转拼音工具类
 */
public class PinyinUtile {
    /**
     * 将汉字转换成拼音
     * @param content
     * @return
     */
    public static String str2Pinyin(String content){

        if (content == null) {
            return null;
        }

        // 关闭音调的设置
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        // 将字符串转成字符数组
        char[] chars = content.toCharArray();

        // 准备一个对象拼接拼音
        StringBuilder stringBuilder = new StringBuilder();

        // 循环字符数组进行拼音转换
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            // 开始使用工具类转换拼音
            String[] pinyin = new String[0];
            try {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(c,format);

                // 判断是否转成了拼音   不为空则成功转成拼音  为空则不是汉字，直接拼接
                if (pinyin != null) {
                    // 可能是多音字  没有词库进行相关判断  随便处理一下
                    // 经过实践证明，多音字取靠后面的拼音对于城市更友好
                    stringBuilder.append(pinyin[pinyin.length-1]);
                }else {
                    stringBuilder.append(c);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(PinyinUtile.str2Pinyin("南京，广州"));
    }


}
