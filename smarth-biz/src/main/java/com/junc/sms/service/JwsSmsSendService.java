/**
 * JwsSmsSendService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.junc.sms.service;

public interface JwsSmsSendService extends javax.xml.rpc.Service {
    public String getSmsSendAddress();

    public JwsSmsSend getSmsSend() throws javax.xml.rpc.ServiceException;

    public JwsSmsSend getSmsSend(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
