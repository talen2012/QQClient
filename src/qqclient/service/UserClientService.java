package qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 17:09
 * 此类的对象用于 用户登录、注册
 * 类的属性，在checkUser中初始化
 * 这样做的目的是，允许前一个用户退出二级菜单后
 * 其它用户再通过一级菜单登录
 */
public class UserClientService {
    // 可能在其它地方用到用户信息，设为属性
    private User user = new User(); // 在验证连接时，即调用checkUser中完成了初始化
    private Socket socket;

    public Boolean checkUser(String userId, String pwd) {
        Boolean result = false;
        // 创建一个Socket, 连接到服务器
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            // 将用户ID、密码封装为一个User对象发送给服务器
            user.setUserId(userId);
            user.setPwd(pwd);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            // 获取服务器返回的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) ois.readObject();
            // 根据Message对象的类型属性，判断是否登录成功
            if ((MessageType.MESSAGE_LOGIN_SUCCEED).equals(msg.getMsgType())) { // 登录成功
                result = true;
                // 创建一个线程保持和服务器的连接，为此新建一个类clientConnectServerThread
                ClientConnectServerThread clientConnectServerThread =
                        new ClientConnectServerThread(userId, socket, oos, ois);
                // 启动客户端用户线程
                clientConnectServerThread.start();
                // 为方便客户端扩展，将客户端用户线程加入到一个集合里
                ClientConnectServerThreadManager.addClientConnectServerThread(userId, clientConnectServerThread);

            } else { // 登录失败
                System.out.println("用户" + user.getUserId() + " 密码" + user.getPwd() + "验证失败");
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void reqOnlineFriends() {
        // 获得当前用户对应的线程
        ClientConnectServerThread ccst =
                ClientConnectServerThreadManager.getClientConnectServerThread(user.getUserId());
        try {
            // 获取线程对应的socket关联的输出流
            ObjectOutputStream oos = ccst.getObjectOutputStream();
            // 创建一个请求在线用户的消息类型
            Message msg = new Message();
            msg.setMsgType(MessageType.MESSAGE_GET_ONLINE_FRIENDS);
            msg.setSender(user.getUserId());
            // 向服务器发送该消息
            oos.writeObject(msg);

            // 回复消息的接收和处理在ClientConnectServerThread里处理...
            // 规定在线用户列表使用 用户1 用户2 用户3...这种方式返回
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        // 获得当前用户对应的线程
        ClientConnectServerThread ccst =
                ClientConnectServerThreadManager.getClientConnectServerThread(user.getUserId());
        try {
            // 获取线程对应的socket关联的输出流
            ObjectOutputStream oos = ccst.getObjectOutputStream();
            // 创建一个请求用户退出的消息类型
            Message msg = new Message();
            msg.setMsgType(MessageType.MESSAGE_CLIENT_EXIT);
            // 向服务器发送该消息
            oos.writeObject(msg);

            // 发送完消息之后
            System.out.println("用户" + user.getUserId() + "退出系统");
            System.exit(0); // 退出进程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
