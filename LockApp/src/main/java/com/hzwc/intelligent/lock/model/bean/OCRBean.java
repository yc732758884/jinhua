package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by mayn on 2018/8/20.
 */

public class OCRBean {

    /**
     * msg : success
     * code : 0
     * ocrText : □白龙子岛
     */

    private String msg;
    private int code;
    private String ocrText;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOcrText() {
        return ocrText;
    }

    public void setOcrText(String ocrText) {
        this.ocrText = ocrText;
    }
}
