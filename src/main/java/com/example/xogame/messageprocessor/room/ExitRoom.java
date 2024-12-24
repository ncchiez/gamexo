package com.example.xogame.messageprocessor.room;

import com.example.shared.Message;
import com.example.xogame.containers.RoomContainer;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.notification.Wrong;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

/**
 * Lớp ExitRoom là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý thông điệp liên quan đến việc người chơi rời khỏi phòng.
 */
public class ExitRoom extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho thông điệp rời phòng.
     * Nếu có lỗi trong quá trình rời phòng, thông báo lỗi sẽ được hiển thị.
     * Nếu không có lỗi, thông điệp sẽ được phân tích và cập nhật trạng thái của phòng.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        // Lỗi
        if (status.equals("WRONG")) {
            Platform.runLater(() -> {
                Wrong.show(appViewController.getContent(), decryptedContent);
            });
            return;
        }

        // Phân tích thông điệp
        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();

        Platform.runLater(() -> {
            // Rời phòng
            if (appViewController.getProfile().getUsername().equals(username)) {
                appViewController.showHome();
                return;
            }

            // Chủ phòng rời phòng
            if (!(appViewController.getContent().getChildren().getLast() instanceof RoomContainer roomContainer)) return;
            if (roomContainer.getUsernameA() != null && roomContainer.getUsernameA().equals(username)) {
                appViewController.showHome();
                return;
            }

            // Người đối diện rời phòng
            roomContainer.removeProfileB();
        });
    }

    /**
     * Tạo phản hồi cho thông điệp rời phòng.
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
