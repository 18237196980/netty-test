package com.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 意在模拟Bio通讯时，一个客户端会占用服务端一个线程（即便客户端不与服务端通讯了还占用着）
 */
public class BioTest {
    public static void main(String[] args) throws Exception {
        /**
         * 注意: 启动后，cmd黑窗口模拟客户端
         * 1.telnet 127.0.0.1 6666
         * 2.ctrl+]
         * 3 send hello1234
         */

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool(); // 创建线程池

        // 创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器已经启动");

        System.out.println("主线程:" + Thread.currentThread()
                                          .getId() + "----" + Thread.currentThread()
                                                                    .getName());


        while (true) {
            // 一直等待建立连接
            System.out.println("服务器等待与客户端建立连接...");
            System.out.println("来个线程:" + Thread.currentThread()
                                               .getId() + "----" + Thread.currentThread()
                                                                         .getName());

            final Socket socket = serverSocket.accept();
            // 此处说明已有客户端连接上server
            System.out.println("服务器已经与客户端建立连接");

            // 创建一个线程，与socket(客户端)通讯
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    hander(socket);
                }
            });
        }
    }

    // 读取客户端发送内容
    private static void hander(Socket socket) {
        try {

            System.out.println("线程：" + Thread.currentThread()
                                             .getId() + "----" + Thread.currentThread()
                                                                       .getName());
            InputStream inputStream = socket.getInputStream();

            byte[] bytes = new byte[1024];
            while (true) {
                System.out.println("真正处理任务的线程：" + Thread.currentThread()
                                                        .getId() + "----" + Thread.currentThread()
                                                                                  .getName());

                int read = inputStream.read(bytes);
                if (read != -1) {
                    // 读取到内容
                    System.out.println("读取到: " + new String(bytes, 0, read));
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
