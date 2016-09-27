package com.familyan.smarth.manager;

/**
 * Created by shaowenchao on 16/9/27.
 */
public interface VerifyCodeManager {

    String send(String mobile);

    boolean validate(String mobile, String identifier, String code);


}
