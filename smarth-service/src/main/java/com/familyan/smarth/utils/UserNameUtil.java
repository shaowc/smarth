package com.familyan.smarth.utils;


import com.familyan.smarth.constants.BindType;

import java.util.regex.Pattern;

/**
 * Created by xifeng on 2015/8/31.
 */
public class UserNameUtil {

    private static Pattern mobilePattern = Pattern.compile("0?(13|15|18|14|17)[0-9]{9}$");

    private static Pattern userNamePattern = Pattern.compile("[0-9a-zA-Z_]{6,30}");

    public static BindType guess(String name){
        if(isMobile(name))
            return BindType.MOBILE;
        if(isEmail(name))
            return BindType.EMAIL;
        return BindType.USER_NAME;
    }

    public static boolean isMobile(String name ){
        return mobilePattern.matcher(name).matches();
    }

    public static boolean isEmail(String name ){
        return name.contains("@");
    }

    public static boolean validateUserName(String userName){
        return userName != null && !userName.trim().equals("") && userNamePattern.matcher(userName).matches();
    }


}
