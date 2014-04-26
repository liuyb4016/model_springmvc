package cn.liuyb.app.service.impl;

public class CurrentUser {
    private static InheritableThreadLocal<Long> users = new InheritableThreadLocal<Long>();

    public static Long getUserId() {
        return users.get();
    }

    public static void setUserId(Long userId) {
        users.set(userId);
    }
}
