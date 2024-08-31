package qqcommon;

import java.io.Serializable;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 14:02
 * 客户端和服务器通信时的消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender; // 发送者
    private String getter; // 接收者
    private String content; // 消息内容
    private String sndTime; // 发送时间
    private String msgType; // 消息类型 [可以在接口定义消息类型]

    // 定义与发送文件相关字段
    private String src; // 源文件目录
    private String dst; // 目标文件目录
    private int fileLength; // 文件字节数
    private byte[] fileBytes; // 文件字节数组

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSndTime() {
        return sndTime;
    }

    public void setSndTime(String sndTime) {
        this.sndTime = sndTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
