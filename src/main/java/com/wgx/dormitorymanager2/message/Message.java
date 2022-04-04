package com.wgx.dormitorymanager2.message;

import java.util.Map;

public class Message {
    private Integer statusCode;
    private String message;
    private Map<Object, Object> attributes;

    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Object, Object> attributes) {
        this.attributes = attributes;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Message success(String message, Map<Object, Object> attributes) {
        Message success = new Message();
        success.setStatusCode(100);
        success.setMessage(message);
        success.setAttributes(attributes);
        return success;
    }
    public static Message fail(String message, Map<Object, Object> attributes) {
        Message fail = new Message();
        fail.setStatusCode(200);
        fail.setMessage(message);
        fail.setAttributes(attributes);
        return fail;
    }
}
