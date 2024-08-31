package qqclient.service;

import java.util.HashMap;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 17:43\
 * 一个集合，管理所有客户端用户线程
 */
public class ClientConnectServerThreadManager {
    // 创建一个HashMap, key是userId, value就是客户端用户线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId, ClientConnectServerThread ccst) {
        hm.put(userId, ccst);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userID) {
        return hm.get(userID);
    }
}
