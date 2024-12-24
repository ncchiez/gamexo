package com.example.xogame.data.account;

/**
 * Lớp EmptyProfile đại diện cho một hồ sơ trống trong trò chơi,
 * được khởi tạo với tên người dùng cụ thể.
 * Kế thừa từ lớp Profile.
 */
public class EmptyProfile extends Profile {
    
    public EmptyProfile(String username) {
        super("__" + username + "__", username, 0, 0, 0, 0, 0);
    }
}
