package com.sub_zet.medal.api;

import com.sub_zet.medal.helpers.Status;

public class Resource<T> {

    private Status status;
    private T data;
    private String message;

    public static <T> Resource<T> success(T data){
        return new Resource<T>(Status.SUCCESS , null , data);
    }

    public static <T> Resource<T> loading(T data){
        return new Resource<T>(Status.LOADING , null , null);
    }

    public static <T> Resource<T> error(T data , String message){
        return new Resource<T>(Status.ERROR , message , data);
    }

    private Resource(Status status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}