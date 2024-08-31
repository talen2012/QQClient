package qqclient.view;

import qqclient.service.ClientMessageService;
import qqclient.service.UserClientService;
import qqclient.utils.Utility;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 14:15
 * 客户端的菜单界面
 */
public class QQView {
    private boolean loop = true; // 控制是否循环显示主菜单
    private ClientMessageService clientMessageService = new ClientMessageService();
    private UserClientService userClientService = new UserClientService(); // 此对象用于用户登录/注册

    public static void main(String[] args) {
        new QQView().showMenu();
    }

    /**
     * 显示主菜单
     */
    public void showMenu() {
        char key; // 接收用户的主菜单选择
        while (loop) {
            System.out.println("=========欢迎登录网络通信系统=========");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.print("请输入您的选择：");

            key = Utility.readMainMenuSelection();
            switch (key) {
                case '1':
                    System.out.println("登录系统...");
                    System.out.print("请输入您的用户ID：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入您的用户密码：");
                    String pwd = Utility.readString(50);

                    // 向服务器验证，代码比较多
                    // 先创建一个类UserClientService, 作为QQView的属性，负责用户登录/注册
                    // 这里调用此类的checkUser方法，向服务器验证用户身份
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("=========欢迎用户" + userId + "登录=========");
                        System.out.println();
                        char key2; // 接收用户二级菜单选择
                        while (loop) {
                            System.out.println("=====网络通信系统二级菜单(用户 " + userId + ")=====");
                            System.out.println("\t\t1 显示在线用户列表");
                            System.out.println("\t\t2 群发消息");
                            System.out.println("\t\t3 私聊消息");
                            System.out.println("\t\t4 发送文件");
                            System.out.println("\t\t9 退出系统");

                            System.out.println("请输入您的选择：");
                            key2 = Utility.readSubMenuSelection();
                            switch (key2) {
                                case '1':
                                    System.out.println("=========在线用户列表=========");
                                    userClientService.reqOnlineFriends();
                                    try {
                                        Thread.sleep(500); // 主线程休眠半秒，等待服务器返回消息
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case '2':
                                    System.out.println("请输入要发给大家的消息：");
                                    String info = Utility.readString(50);
                                    clientMessageService.sendMsgToAll(info, userId);
                                    break;
                                case '3':
                                    System.out.print("请输入想要聊天的用户号：");
                                    String getterId = Utility.readString(50);
                                    System.out.println("请输入发送给对方的内容：");
                                    String content = Utility.readString(50);
                                    clientMessageService.sendMsgToOne(content, userId, getterId);
                                    break;
                                case '4':
                                    System.out.print("请输入想要发送给文件的用户：");
                                    String getterId2 = Utility.readString(50);
                                    System.out.print("请输入源文件地址：");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入对方存储文件的地址：");
                                    String dst = Utility.readString(100);
                                    clientMessageService.sendFileToOne(userId, getterId2, src, dst);
                                    break;
                                case '9':
                                    userClientService.logOut();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败！");
                    }
                    break;
                case '9':
                    loop = false;
                    break;
            }
        }
        System.out.println("===========退出网络通信系统===========");
    }
}
