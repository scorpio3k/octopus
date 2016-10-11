package org.scorpio.octopus;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 判断字符串是否是<code>null</code>或者空串<code>""</code>
     * <pre>
     *   StringUtils.isEmpty(null) = true
     *   StringUtils.isEmpty("") = true
     *   StringUtils.isEmpty(" ") = false
     *   StringUtils.isEmpty("bob") = false
     *   StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不是<code>null</code>或者空串<code>""</code>
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否是空白字符或空串<code>""</code>或<code>null</code>
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        int strLen;
        if(str == null || (strLen = str.length()) == 0){
            return true;
        }
        for(int i=0; i<strLen; i++){
            if((Character.isWhitespace(str.charAt(i))) == false){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不是空白字符或空串<code>""</code>或<code>null</code>
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return !StringUtils.isBlank(str);
    }

    /**
     * 删除字符串两端的空白字符,传入<code>null</code>将返回<code>null</code>
     * @param str
     * @return
     */
    public static String trim(String str){
        return str == null ? null : str.trim();
    }

    /**
     * 删除字符串两端的空白字符,如果字符串经过处理后变为空串或者传入<code>null</code>将返回<code>null</code>
     * @param str
     * @return
     */
    public static String trimToNull(String str){
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * 删除字符串两端的空白字符,如果传入<code>null</code>将返回<code>""</code>
     * @param str
     * @return
     */
    public static String trimToEmpty(String str){
        return str == null ? EMPTY : str.trim();
    }

    /**
     *  <p>删除字符串前端特定的字符,通过stripChars指定这些特定的字符。</p>
     * 如果<code>stripChars==null</code>则删除前端空白字符
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripStart(String str, String stripChars){
        int strLen;
        if(str == null || (strLen = str.length()) == 0){
            return str;
        }

        int start = 0;
        if(stripChars == null){
            while ((start != strLen) && Character.isWhitespace(str.charAt(start))){
                start ++;
            }
        }else if(stripChars.length() == 0){
            return str;
        }else{
            while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND)){
                start ++;
            }
        }
        return str.substring(start);
    }

    /**
     *  <p>删除字符串后端特定的字符,通过stripChars指定这些特定的字符。</p>
     * 如果<code>stripChars==null</code>则删除后端空白字符
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripEnd(String str, String stripChars){
        int end;
        if(str == null || (end = str.length()) == 0){
            return str;
        }
        if(stripChars == null){
            while ((end != 0) && Character.isWhitespace(str.charAt(end -1))){
                end --;
            }
        }else if(stripChars.length() == 0){
            return str;
        }else{
            while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND)){
                end --;
            }
        }
        return str.substring(0, end);
    }


    /**
     * <p>删除字符串两端特定的字符,通过stripChars指定这些特定的字符。</p>
     * 如果<code>stripChars==null</code>则删除两端空白字符
     * @param str
     * @param stripChars
     * @return
     */
    public static String strip(String str, String stripChars){
        if(isEmpty(str)){
            return str;
        }
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * 删除字符串两端的空白字符
     */
    public static String strip(String str){
        return str == null ? EMPTY : strip(str, null);
    }

    /**
     * <p>批量删除字符串数组两端特定的字符,通过stripChars指定这些特定的字符。</p>
     * 如果<code>stripChars==null</code>则删除两端空白字符
     * @param strs
     * @param stripChars
     * @return
     */
    public static String[] stripAll(String[] strs, String stripChars){
        int strsLen;
        if(strs == null || (strsLen = strs.length) == 0){
            return strs;
        }
        String[] newArr = new String[strsLen];
        for(int i=0; i<strsLen; i++){
            newArr[i] = strip(strs[i], stripChars);
        }
        return newArr;
    }

    /**
     * 批量删除字符串数组中两端空白字符
     * @param strs
     * @return
     */
    public static String[] stripAll(String[] strs){
        return stripAll(strs, null);
    }

    /**
     * 判断两个字符串是否相等,<code>null</code>被认为是相等的
     */
    public static boolean equals(String str1, String str2){
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 忽略大小写判断两个字符串是否相等，<code>null</code>被认为是相等的
     */
    public static boolean equalsIgnoreCase(String str1, String str2){
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 从前向后找到匹配字符串的索引，<code>null</code>值将返回-1
     */
    public static int ordinalIndexOf(String str, String searchStr, int ordinal){
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }

    private static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex){
        if(str == null || searchStr == null || ordinal <=0 ){
            return INDEX_NOT_FOUND;
        }
        if(searchStr.length() == 0){
            return lastIndex ? str.length() : 0;
        }
        int found = 0;
        int index = lastIndex ? str.length() : INDEX_NOT_FOUND;
        do{
            if(lastIndex){
                index = str.lastIndexOf(searchStr, index -1);
            }else{
                index = str.indexOf(searchStr, index + 1);
            }
            if(index < 0){
                return index;
            }
            found ++;
        }while (found < ordinal);
        return index;
    }

    /**
     * 从指定的索引处查找匹配的字符串的索引
     */
    public static int indexOf(String str, String searchStr, int startPos){
        if(str == null || searchStr == null){
            return INDEX_NOT_FOUND;
        }
        if(searchStr.length() == 0 && startPos >= str.length()){
            return str.length();
        }
        return str.indexOf(searchStr, startPos);
    }

    /**
     * 忽略大小写查找第一个匹配字符串的索引
     */
    public static int indexOfIgnoreCase(String str, String searchStr){
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    /**
     * 从指定的索引处忽略大小写查找第一个匹配字符串的索引
     */
    public static int indexOfIgnoreCase(String str, String searchStr, int startPos){
        if(str == null || searchStr == null){
            return INDEX_NOT_FOUND;
        }
        if(startPos < 0){
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if(startPos > endLimit){
            return INDEX_NOT_FOUND;
        }
        if(searchStr.length() == 0){
            return startPos;
        }
        for(int i = startPos; i< endLimit; i++){
            if(str.regionMatches(true, i, searchStr, 0, searchStr.length())){
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找最后一个匹配字符串的索引
     */
    public static int lastIndexOf(String str, String searchStr){
        if(str == null || searchStr == null){
            return INDEX_NOT_FOUND;
        }
        return str.lastIndexOf(searchStr);
    }

    public static boolean contains(String str, String searchStr){
        if(str == null || searchStr == null){
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

    public static boolean containsIgnoreCase(String str, String searchStr){
        if(str == null || searchStr == null){
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for(int i=0; i<=max; i++){
            if(str.regionMatches(true, i, searchStr, 0, len)){
                return true;
            }
        }
        return false;
    }

    public static String substring(String str, int start){
        if(str == null){
            return null;
        }
        if(start < 0){
            start = str.length() + start;
        }
        if(start < 0){
            start = 0;
        }
        if(start > str.length()){
            return EMPTY;
        }
        return str.substring(start);
    }

    public static String substring(String str, int start, int end){
        if(str == null){
            return null;
        }
        if(end < 0){
            end = str.length() + end;
        }
        if(start < 0){
            start = str.length() + start;
        }
        if(end > str.length()){
            end = str.length();
        }
        if(start > end){
            return  EMPTY;
        }
        if(start < 0){
            start = 0;
        }
        if(end < 0){
            end = 0;
        }
        return str.substring(start, end);
    }

    /**
     * 获取索引左侧字符串,不会抛异常
     */
    public static String left(String str, int len){
        if(str == null){
            return null;
        }
        if(len < 0){
            return EMPTY;
        }
        if(str.length() <= len){
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * 获取索引右侧字符串，不会抛异常
     */
    public static String right(String str, int len){
        if(str == null){
            return null;
        }
        if(len < 0){
            return EMPTY;
        }
        if(str.length() <= len){
            return str;
        }
        return str.substring(str.length() - len);
    }

    public static String substringBefore(String str, String separator){
        if(isEmpty(str) || separator == null){
            return str;
        }
        if(separator.length() == 0){
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if(pos == INDEX_NOT_FOUND){
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 获取第一个匹配字符串右侧的字符串，不包含匹配字符串
     */
    public static String substringAfter(String str, String separator){
        if(isEmpty(str)){
            return str;
        }
        if(separator == null){
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if(pos == INDEX_NOT_FOUND){
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 获取最后一个匹配字符串左侧的字符串，不包含匹配字符串
     */
    public static String substringBeforeLast(String str, String separator){
        if(isEmpty(str) || isEmpty(separator)){
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if(pos == INDEX_NOT_FOUND){
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 获取最后一个匹配字符串右侧的字符串，不包含匹配字符串
     */
    public static String substringAfterLast(String str, String separator){
        if(isEmpty(str)){
            return str;
        }
        if(isEmpty(separator)){
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if(pos == INDEX_NOT_FOUND || pos == (str.length() - separator.length())){
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }


}
