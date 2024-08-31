package qqcommon;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 14:08
 */
public interface MessageType {
    // 接口中定义了一些常量，表示不同的消息类型
    // 接口中的属性，默认是public static final的
    String MESSAGE_LOGIN_SUCCEED = "1"; // 表示登录成功
    String MESSAGE_LOGIN_FAIL = "2"; // 表示登录失败
    String MESSAGE_COMMON_MSG = "3"; // 表示普通消息
    String MESSAGE_GET_ONLINE_FRIENDS = "4"; // 表示请求在线用户
    String MESSAGE_RET_ONLINE_FRIENDS = "5"; // 表示返回在线用户
    String MESSAGE_CLIENT_EXIT = "6"; // 表示用户退出
    String MESSAGE_TO_ALL_MSG = "7"; // 表示群发消息
    String MESSAGE_FILE_MSG = "8"; // 表示文件消息
    String MESSAGE_SERVER_MSG = "9"; // 表示服务器推送消息
    String MESSAGE_INVALID_GETTER = "10"; // 表示服务器检查到用户不存在
}
