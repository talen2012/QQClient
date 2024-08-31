package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;
import java.net.Socket;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 17:27
 * 此类的一个对象和客户端保持连接
 */
public class ClientConnectServerThread extends Thread {
    private String userId;
    private Socket socket;
    private ObjectOutputStream oos; // 避免创建多个ObjectOutputStream实例，写入同一个OutputStream导致流头部信息冲突
    private ObjectInputStream ois;

    public ClientConnectServerThread(String userId, Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
        this.userId = userId;
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }

    @Override
    public void run() {
        // 通信要保持，所以采用while循环
        while (true) {
            try {
                System.out.println("用户" + userId + "和服务器保持通信，读取数据...");
                Message msg = (Message) ois.readObject(); // 没有消息就阻塞

                // 根据消息类型，选择对应的处理
                if (MessageType.MESSAGE_RET_ONLINE_FRIENDS.equals(msg.getMsgType())) {
                    // 服务器返回在线用户列表
                    String[] onlineFriends = msg.getContent().split(" ");
                    for (int i = 0; i < onlineFriends.length; i++) {
                        System.out.println("用户：" + onlineFriends[i]);
                    }
                } else if (MessageType.MESSAGE_COMMON_MSG.equals(msg.getMsgType())) {
                    // 展示收到的普通消息
                    System.out.println(msg.getSender() + " 对 " + msg.getGetter() + " 说：" + msg.getContent());
                } else if (MessageType.MESSAGE_TO_ALL_MSG.equals(msg.getMsgType())) {
                    // 展示收到的群发消息
                    System.out.println(msg.getSender() + "对大家说：" + msg.getContent());
                } else if (MessageType.MESSAGE_FILE_MSG.equals(msg.getMsgType())) {
                    // 收到文件消息
                    System.out.println(msg.getSender() + "发文件给" + msg.getGetter() + "：源文件"
                            + msg.getSrc() + " 目标文件" + msg.getDst());
                    // 保存文件
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(msg.getDst()));
                    bos.write(msg.getFileBytes());
                    System.out.println("保存文件成功!");
                    bos.close();
                } else if (MessageType.MESSAGE_SERVER_MSG.equals(msg.getMsgType())) {
                    // 展示服务器推送的消息
                    System.out.println("服务器推送消息给所有人 说: " + msg.getContent());
                } else if (MessageType.MESSAGE_INVALID_GETTER.equals(msg.getMsgType())) {
                    System.out.println("接收消息的用户不存在！");
                } else {
                    System.out.println("暂时无该消息类型的处理方法");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
