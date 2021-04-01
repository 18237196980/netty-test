package com.bio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapperByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\file.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mapBuffer.put(0, (byte) 'R');
        mapBuffer.put(3, (byte) 't');

        randomAccessFile.close();
        System.out.println("修改成功");


    }
}
