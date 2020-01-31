package com.todd.lucene.util;

import lombok.Data;

@Data
public class ReturnResult {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 展示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;


    public static ReturnResult getSuccessInstance(String msg,Object data){
        ReturnResult result = new ReturnResult();
        result.setSuccess(Boolean.TRUE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }



}
