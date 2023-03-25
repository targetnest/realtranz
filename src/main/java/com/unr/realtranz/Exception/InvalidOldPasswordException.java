package com.unr.realtranz.Exception;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:19-01-2023 00:40
 **/
public class InvalidOldPasswordException extends Throwable {
    private static final long serialVersionUID = 1L;
    public InvalidOldPasswordException(){
        super("Invalid Old Password Exception");
    }
}
