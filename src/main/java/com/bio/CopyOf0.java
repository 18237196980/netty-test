package com.bio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class CopyOf0 {
    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("D:\\m4.jpg");
        FileChannel channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\m.jpg");
        FileChannel channel2 = fileOutputStream.getChannel();

        channel2.transferFrom(channel, 0, channel.size());

        channel.close();
        channel2.close();

        fileInputStream.close();
        fileOutputStream.close();
    }
}
