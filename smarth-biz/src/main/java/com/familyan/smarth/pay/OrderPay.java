package com.familyan.smarth.pay;

import java.util.Date;
import java.util.Map;

/**
 * Created by shaowenchao on 16/8/5.
 */
public interface OrderPay {

    Map<String, String> unifiedorder(Integer orderId, String ip);

    String notify(Map<String, String> payResult);


}
