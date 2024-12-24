package com.example.xogame.messageprocessor.log;

import com.example.shared.Message;
import com.example.xogame.data.account.Account;
import com.example.xogame.data.account.Profile;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.notification.ConnectionError;
import com.example.xogame.notification.Wrong;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

/**
 * Lớp Login là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý các yêu cầu đăng nhập của người dùng và cập nhật giao diện.
 */
public class Login extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho yêu cầu đăng nhập.
     * Nếu trạng thái là "WRONG", hiển thị thông báo lỗi.
     * Nếu trạng thái là "OK", chuyển đổi nội dung thông điệp thành đối tượng Account và Profile,
     * sau đó cập nhật giao diện người dùng với thông tin tài khoản và hiển thị trang chính.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        if (appViewController == null) return;

        if (status.equals("WRONG")) {
            Platform.runLater(() -> {
                Wrong.show(appViewController.getContent(), decryptedContent);
            });
            return;
        }

        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();

        // Chuyển đối tượng JsonObject thành đối tượng Account và Profile
        Gson gson = new Gson();
        Account account = gson.fromJson(jsonObject, Account.class);
        Profile profile = gson.fromJson(jsonObject, Profile.class);
        String token = jsonObject.get("token").getAsString();

        Platform.runLater(() -> {
            appViewController.setAccount(account);
            appViewController.setProfile(profile);
            appViewController.setToken(token);
            appViewController.showHome();
        });
    }

    /**
     * Tạo phản hồi cho yêu cầu đăng nhập.
     * Trong lớp này, phương thức trả về null vì không có phản hồi được tạo.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @return null
     */
    @Override
    protected Message generateResponse(String decryptedContent) {
        return null;
    }
}
