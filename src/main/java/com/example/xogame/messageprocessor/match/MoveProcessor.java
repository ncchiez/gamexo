package com.example.xogame.messageprocessor.match;

import com.example.shared.Message;
import com.example.xogame.data.match.Move;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

/**
 * Lớp MoveProcessor là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý thông điệp di chuyển trong trò chơi.
 */
public class MoveProcessor extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho thông điệp di chuyển.
     * Phân tích thông điệp để lấy thông tin về di chuyển và giới hạn thời gian,
     * sau đó cập nhật trạng thái trò chơi trên giao diện người dùng.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        // Phân tích thông điệp
        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();
        Move move = new Gson().fromJson(jsonObject, Move.class);
        int timeLimit = jsonObject.get("timeLimit").getAsInt();

        Platform.runLater(() -> {
            this.gameOnlineController.move(move, timeLimit);
            this.gameOnlineController.setStep(move.getStep() + 1);
        });
    }

    /**
     * Tạo phản hồi cho thông điệp di chuyển.
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
