package com.netty.rpc.service;

/**
 * 服务方提供的方法
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String getMsg(String msg) {
        if (msg != null) {
            return "你好啊，我是rpc远程调用:" + msg;
        } else {
            return "你好啊，我是rpc远程调用";
        }
    }
}
