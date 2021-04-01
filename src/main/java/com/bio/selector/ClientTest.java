package com.bio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientTest {

    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if (!socketChannel.connect(inetSocketAddress)) {
            // 没有连接成功
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端连接需要时间，连接上之前可以做其它事情...");
            }
        }

        // 如果连接上了，则发送数据
        ByteBuffer byteBuffer = ByteBuffer.wrap("我是小尚".getBytes());

        socketChannel.write(byteBuffer);

        System.in.read();
    }

}
