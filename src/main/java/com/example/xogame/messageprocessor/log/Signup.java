package com.example.xogame.messageprocessor.log;

import com.example.shared.Message;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.notification.Wrong;
import javafx.application.Platform;

/**
 * Lớp Signup là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý các yêu cầu đăng ký của người dùng và hiển thị thông báo lỗi nếu có.
 */
public class Signup extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho yêu cầu đăng ký.
     * Nếu trạng thái là "WRONG", hiển thị thông báo lỗi cho người dùng.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        if (status.equals("WRONG")) {
            Platform.runLater(() -> {
                Wrong.show(appViewController.getContent(), decryptedContent);
            });
        }
    }

    /**
     * Tạo phản hồi cho yêu cầu đăng ký.
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
