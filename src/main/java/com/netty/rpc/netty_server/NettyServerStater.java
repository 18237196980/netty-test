package com.netty.rpc.netty_server;

public class NettyServerStater {
    public static void main(String[] args) {
        try {
            NettyServer.serverStart("127.0.0.1", 6668);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
