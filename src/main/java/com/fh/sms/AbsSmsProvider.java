package com.fh.sms;


import java.io.UnsupportedEncodingException;



public abstract class AbsSmsProvider implements ISmsProvider{
	
	@Override
    public Result send(String mobile,String content){
        Result result = null;
        try{
            result = doSend( mobile, content);
        }catch (Exception e){
        	result=new Result();
        	result.setResponseContent(e.getMessage());
        }
        return result;
    }
    protected String getFormatContent(String content){
        return content.replaceAll(" ", "");
    }
    protected abstract String getProviderName();

    protected abstract String getProviderURL() throws UnsupportedEncodingException;

    protected abstract String getAccount();

    protected abstract String getPass();

    protected abstract String getCharset();

    protected abstract Result doSend(String mobile,String content)throws Exception;


}
