/**
 * JwsSmsSend.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.junc.sms.service;

public interface JwsSmsSend extends java.rmi.Remote {
    public String sendV3(String username, String password, String content, String mobile, String ext, String dstime, String msgid, String msgfmt) throws java.rmi.RemoteException;
    public String send(String username, String password, String content, String mobile, String dstime) throws java.rmi.RemoteException;
}
