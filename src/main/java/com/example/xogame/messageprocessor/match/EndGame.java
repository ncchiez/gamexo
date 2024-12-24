package com.example.xogame.messageprocessor.match;

import com.example.shared.Message;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

/**
 * Lớp EndGame là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý các yêu cầu kết thúc trò chơi và cập nhật giao diện người dùng với thông tin kết quả.
 */
public class EndGame extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho yêu cầu kết thúc trò chơi.
     * Phân tích thông điệp để lấy thông tin về người chiến thắng và điểm số,
     * sau đó gọi phương thức kết thúc trò chơi trên controller với thông tin đó.
     * Thực hiện việc này trong một luồng riêng để tránh block giao diện.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        if (gameOnlineController == null) return;

        // Phân tích thông điệp
        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();
        String winner = jsonObject.get("winner").getAsString();
        int score = jsonObject.get("score").getAsInt();

        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Xử lý kết thúc game
            Platform.runLater(() -> {
                gameOnlineController.endGame(winner, score);
            });
        }).start();
    }

    /**
     * Tạo phản hồi cho yêu cầu kết thúc trò chơi.
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
