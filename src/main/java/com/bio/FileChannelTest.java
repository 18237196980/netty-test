package com.bio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 将内容写入文件
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        String str = "hello,尚硅谷";

        FileOutputStream inputStream = new FileOutputStream("D:\\file.txt");

        FileChannel channel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        channel.write(byteBuffer);

        inputStream.close();

    }
}
