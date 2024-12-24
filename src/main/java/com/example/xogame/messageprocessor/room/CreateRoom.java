package com.example.xogame.messageprocessor.room;

import com.example.shared.Message;
import com.example.xogame.data.account.Profile;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.notification.Wrong;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

/**
 * Lớp CreateRoom là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý thông điệp liên quan đến việc tạo phòng chơi trong trò chơi.
 */
public class CreateRoom extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho thông điệp tạo phòng.
     * Nếu có lỗi trong quá trình tạo phòng, thông báo lỗi sẽ được hiển thị.
     * Nếu không có lỗi, thông điệp sẽ được phân tích và cập nhật trạng thái phòng chơi.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        // Lỗi tạo phòng
        if (status.equals("WRONG")) {
            Platform.runLater(() -> {
                Wrong.show(appViewController.getContent(), decryptedContent);
            });
            return;
        }

        // Phân tích thông điệp
        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();
        String idRoom = jsonObject.get("idRoom").getAsString();

        Platform.runLater(() -> {
            appViewController.showRoom(idRoom);
            appViewController.updateRoom();
        });
    }

    /**
     * Tạo phản hồi cho thông điệp tạo phòng.
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
