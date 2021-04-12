package com.netty.tcp;

import java.util.Arrays;

/**
 * tcp协议对象
 */
public class TcpXieYiModel {

    /**
     * 数据长度
     */
    private int len;
    /**
     * 数据内容
     */
    private byte[] content;

    public TcpXieYiModel(int len, byte[] content) {
        this.len = len;
        this.content = content;
    }

    public TcpXieYiModel() {
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TcpXieYiModel{" +
                "len=" + len +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
