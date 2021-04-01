package com.bio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * selector服务端
 */
public class SelectorServerTest {

    public static void main(String[] args) throws Exception {

        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 创建Selector
        Selector selector = Selector.open();

        // 绑定serverSocketChannel监听的端口
        serverSocketChannel.socket()
                           .bind(new InetSocketAddress(6666));

        // 设置serverSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 将serverSocketChannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务端等待了1s，无连接");
                continue;
            }
            // 监听selectkey
            System.out.println("有连接...");

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                System.out.println("selectionKeys:" + selectionKeys.size());
                // 获取key
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    System.out.println("aa:");

                    // 如果当前key是连接事件,则在服务端生成一个新的SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 将socketChannel注册到selector
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    System.out.println("bb:");

                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("server端收到客户端发送的信息:" + new String(byteBuffer.array()));
                }

                keyIterator.remove();
            }

        }

    }

}
