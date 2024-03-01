package com.doBattle.mydoBattle.error;

public class MemberDuplicateException extends RuntimeException{
    public MemberDuplicateException(String message){
        super(message);
    }
}
