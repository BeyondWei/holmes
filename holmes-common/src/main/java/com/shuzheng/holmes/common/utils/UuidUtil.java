package com.shuzheng.holmes.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by 子华 on 2017/6/22.
 * 生成唯一字符串，已UUID为基础生成
 */
public class UuidUtil {

    private static String[] _09_aZ={"0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private static int maxSize = _09_aZ.length;

    private static Random random = new Random();

    public static  String getUuid(){
        String uuid= UUID.randomUUID().toString();
        uuid=uuid.replace("-","");
        return  uuid;
    }

    public static void main(String[] args) {
        System.out.println(uniqueNo16());
    }

    public static final String uniqueNo16(){
        int i = random.nextInt(24);
        StringBuffer current =new StringBuffer(getUuid().substring(i,i+8));
        StringBuffer sb = differ(8);
        return reassemble(current,sb);
    }

    public static final String uniqueNo64(){
        StringBuffer current = new StringBuffer(getUuid());
        StringBuffer sb = differ(64-current.length());
        return reassemble(current,sb);
    }

    public static final String uniqueNo128(){
        StringBuffer current = new StringBuffer(getUuid());
        StringBuffer sb = differ(128-current.length());
        return reassemble(current,sb);
    }

    private static  StringBuffer differ(int differ){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<differ;i++){
            sb.append(_09_aZ[random.nextInt(maxSize)]);
        }
        return sb;
    }


    private static String reassemble(StringBuffer soure,StringBuffer target){
        for (int i=0;i<target.length();i++){
            String s = target.substring(i,i+1);
            int at = random.nextInt(soure.length());
            soure.insert(at,s);
        }
        return soure.toString();
    }
}
