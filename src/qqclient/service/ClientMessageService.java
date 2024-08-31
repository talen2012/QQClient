package qqclient.service;

import qqclient.utils.StreamUtils;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 23:57
 * 此类的对象，提供私聊、群聊的实现
 */
public class ClientMessageService {
    public void sendMsgToOne(String content, String senderId, String getterId) {
        // 打包成一个对应类型的messge
        System.out.println(senderId + " 对 " + getterId + " 说：" + content);
        Message msg = new Message();
        msg.setMsgType(MessageType.MESSAGE_COMMON_MSG);
        msg.setSender(senderId);
        msg.setGetter(getterId);
        msg.setContent(content);

        // 获取sender的socket关联的输出流
        try {
            ClientConnectServerThread ccst = ClientConnectServerThreadManager.getClientConnectServerThread(senderId);
            ccst.getObjectOutputStream().writeObject(msg); // 使用用户线程中创建的唯一ObjectOutputStream实例
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgToAll(String content, String senderId) {
        // 打包成一个对应类型的messge
        System.out.println(senderId + " 对大家说：" + content);
        Message msg = new Message();
        msg.setMsgType(MessageType.MESSAGE_TO_ALL_MSG);
        msg.setSender(senderId);
        msg.setContent(content);

        // 获取sender的socket关联的输出流
        try {
            ClientConnectServerThread ccst = ClientConnectServerThreadManager.getClientConnectServerThread(senderId);
            ccst.getObjectOutputStream().writeObject(msg); // 使用用户线程中创建的唯一ObjectOutputStream实例
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFileToOne(String senderId, String getterId, String src, String dst) {
        // 打包成一个对应类型的messge
        System.out.println(senderId + "发文件给" + getterId + "：源文件" + src + " 目标文件" + dst);
        Message msg = new Message();
        msg.setMsgType(MessageType.MESSAGE_FILE_MSG);
        msg.setSender(senderId);
        msg.setGetter(getterId);
        msg.setSrc(src);
        msg.setDst(dst);

        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            byte[] fileBytes = StreamUtils.streamToByteArray(bis);
            msg.setFileBytes(fileBytes);
            msg.setFileLength(fileBytes.length);

            ClientConnectServerThread ccst = ClientConnectServerThreadManager.getClientConnectServerThread(senderId);
            ccst.getObjectOutputStream().writeObject(msg); // 使用用户线程中创建的唯一ObjectOutputStream实例
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
