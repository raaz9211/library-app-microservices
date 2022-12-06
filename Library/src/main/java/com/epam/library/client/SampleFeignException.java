package com.epam.library.client;


import feign.FeignException;



public class SampleFeignException extends FeignException{
    public SampleFeignException(int status,String message) {
        super(status,message);
    }



}