package com.bio;

import com.bio.util.BufferToStr;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * FileChannel 将文件内容读取到ByteBuffer,然后打印
 */
public class FileChannelTest2 {
    public static void main(String[] args) throws Exception {

        FileInputStream inputStream = new FileInputStream("D:\\file.txt");

        FileChannel channel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        inputStream.close();

    }


}
