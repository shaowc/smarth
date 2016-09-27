/**
 * JwsSmsSendServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.junc.sms.service;

public class JwsSmsSendServiceLocator extends org.apache.axis.client.Service implements JwsSmsSendService {

    public JwsSmsSendServiceLocator() {
    }


    public JwsSmsSendServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JwsSmsSendServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SmsSend
    private String SmsSend_address = "http://www.jc-chn.cn/services/SmsSend";

    public String getSmsSendAddress() {
        return SmsSend_address;
    }

    // The WSDD service name defaults to the port name.
    private String SmsSendWSDDServiceName = "SmsSend";

    public String getSmsSendWSDDServiceName() {
        return SmsSendWSDDServiceName;
    }

    public void setSmsSendWSDDServiceName(String name) {
        SmsSendWSDDServiceName = name;
    }

    public JwsSmsSend getSmsSend() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SmsSend_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSmsSend(endpoint);
    }

    public JwsSmsSend getSmsSend(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SmsSendSoapBindingStub _stub = new SmsSendSoapBindingStub(portAddress, this);
            _stub.setPortName(getSmsSendWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSmsSendEndpointAddress(String address) {
        SmsSend_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (JwsSmsSend.class.isAssignableFrom(serviceEndpointInterface)) {
                SmsSendSoapBindingStub _stub = new SmsSendSoapBindingStub(new java.net.URL(SmsSend_address), this);
                _stub.setPortName(getSmsSendWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("SmsSend".equals(inputPortName)) {
            return getSmsSend();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.jc-chn.cn/services/SmsSend", "JwsSmsSendService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.jc-chn.cn/services/SmsSend", "SmsSend"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("SmsSend".equals(portName)) {
            setSmsSendEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
