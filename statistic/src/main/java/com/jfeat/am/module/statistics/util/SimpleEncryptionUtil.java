package com.jfeat.am.module.statistics.util;

import com.jfeat.am.module.statistics.services.crud.model.RangeModel;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleEncryptionUtil
{
    private static final Integer left_Lowercase_letters = 65;
    private static final Integer right_Lowercase_letters = 90;

    //大写字母
    private static final Integer left_capital = 97;
    private static final Integer right_capital = 125;

    //字母
    private static final Integer left_num = 48;
    private static final Integer right_num =  57;

    private static final Integer left_all = 48;
    private static final Integer right_all = 126;

    private static final Integer RANDOM_KEY_LENGTH = 2;

    private static List<RangeModel> rangeModelList = null;

    public static List<RangeModel>  getRangeModelList(){
        if(rangeModelList == null || rangeModelList.size() == 0){
            rangeModelList = new ArrayList<>();
            rangeModelList.add(new RangeModel(58,64));
            rangeModelList.add(new RangeModel(91,96));
        }

        return rangeModelList;
    }

    public static String decrypt(String code, Integer deviation){
        deviation = checkDeviation(deviation);
        String randomString = code.substring(code.length() - RANDOM_KEY_LENGTH, code.length());
        code = code.substring(0,code.length()-RANDOM_KEY_LENGTH);

        Integer randomDeviation = getDeviation(randomString);

        char[] chars = code.toCharArray();
        //整体偏移
        StringBuilder encryptStringBud = new StringBuilder();

        for(char cha:chars){
            int asciiCode = Integer.valueOf(cha);
            asciiCode = decryptRangeChar(left_all,right_all,right_all-left_all+1,deviation*10+randomDeviation,asciiCode);
            encryptStringBud.append((char)asciiCode);
        }

        chars = encryptStringBud.toString().toCharArray();
        encryptStringBud = new StringBuilder();
        for(char cha:chars){
            int asciiCode = Integer.valueOf(cha);
            /****** 小写字母处理  ***/
            asciiCode = decryptRangeChar(left_capital,right_capital,29,deviation,asciiCode);
            /****** 大写写字母处理  ***/
            asciiCode = decryptRangeChar(left_Lowercase_letters,right_Lowercase_letters,26,deviation,asciiCode);
            /****** 数字处理  ***/
            asciiCode = decryptRangeChar(left_num,right_num,10,deviation,asciiCode);

            encryptStringBud.append((char)asciiCode);
        }

        return encryptStringBud.toString();
    }

    //加密
    public static String encryption(String code, Integer deviation){
        //偏移量处理
        deviation = checkDeviation(deviation);

        char[] chars = code.toCharArray();
        StringBuilder encryptStringBud = new StringBuilder();
        for(char cha:chars){
            int asciiCode = Integer.valueOf(cha);
            /****** 小写字母处理  ***/
            asciiCode = encryptionRangeChar(left_capital,right_capital,29,deviation,asciiCode);
            /****** 大写写字母处理  ***/
            asciiCode = encryptionRangeChar(left_Lowercase_letters,right_Lowercase_letters,26,deviation,asciiCode);
            /****** 数字处理  ***/
            asciiCode = encryptionRangeChar(left_num,right_num,10,deviation,asciiCode);
            encryptStringBud.append((char)asciiCode);
        }

        char[] newChars = encryptStringBud.toString().toCharArray();
        //初始化
        encryptStringBud = new StringBuilder();

        //额外偏移参数
        String randomCode =   RandomStringUtils.random(RANDOM_KEY_LENGTH, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
        Integer randomDeviation = getDeviation(randomCode);

        for(char c :newChars){
            int asciiCode = Integer.valueOf(c);
            asciiCode = encryptionRangeChar(left_all,right_all,right_all-left_all+1,deviation*10 + randomDeviation ,asciiCode);
            encryptStringBud.append((char)asciiCode);
        }

        encryptStringBud.append(randomCode);

        return encryptStringBud.toString();
    }

    //根据字符串计算 获取偏移量
    static Integer  getDeviation(String randomString){
        char[] randomChars = randomString.toCharArray();
        Integer integer = 0;
        for (char c:randomChars){
            integer += Integer.valueOf(c);
        }
        integer = integer%5;
        return integer;
    }

    public static int encryptionRangeChar(Integer leftRange,Integer rightRange,Integer total,Integer deviation,int asciiCode){
        if( asciiCode<= (rightRange - deviation) && asciiCode >= leftRange ){
            asciiCode += deviation;
        }else
        if(asciiCode > rightRange - deviation && asciiCode <= rightRange){
            //26位字母内部循环
            asciiCode = asciiCode - total + deviation;
        }
        return asciiCode;
    }



    public static int decryptRangeChar(Integer leftRange,Integer rightRange,Integer total,Integer deviation,int asciiCode){
        if( asciiCode<= rightRange   && asciiCode >= (leftRange + deviation)){
            asciiCode -= deviation;
        }else
        if(asciiCode >= leftRange  && (asciiCode < leftRange+ deviation) ){
            asciiCode = asciiCode + total - deviation;
        }
        return asciiCode;
    }


    public static Integer checkDeviation(Integer deviation){
        if(deviation == null || deviation <= 0){deviation = 1 ;}
        if(deviation > 9){ deviation = 9;}
        return deviation;
    }
}
