package org.scorpio.octopus.file;

/**
 * Created by 16101256-8 on 2016/10/9.
 */
public class FileUtils {
    /**
     * 获取文件后缀
     */
    public static String getSuffix(String fileName){
        String[] token = fileName.split("\\.");
        if(token.length > 0){
            return token[token.length - 1];
        }else{
            return "";
        }
    }
}
