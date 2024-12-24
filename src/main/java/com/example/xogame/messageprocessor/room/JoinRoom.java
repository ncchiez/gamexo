package com.example.xogame.messageprocessor.room;

import com.example.shared.Message;
import com.example.xogame.data.account.Profile;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.notification.Wrong;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;

/**
 * Lớp JoinRoom là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý thông điệp liên quan đến việc người chơi tham gia vào một phòng chơi.
 */
public class JoinRoom extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho thông điệp tham gia phòng.
     * Nếu có lỗi trong quá trình tham gia, thông báo lỗi sẽ được hiển thị.
     * Nếu không có lỗi, thông điệp sẽ được phân tích và cập nhật thông tin phòng.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        // Lỗi vào phòng
        if (status.equals("WRONG")) {
            Platform.runLater(() -> {
                Wrong.show(appViewController.getContent(), decryptedContent);
            });
            return;
        }

        // Phân tích thông điệp
        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();
        String idRoom = jsonObject.get("idRoom").getAsString();

        Profile profileA = getProfile(jsonObject, "A");
        Profile profileB = getProfile(jsonObject, "B");

        Platform.runLater(() -> {
            appViewController.updateRoom(idRoom, profileA, profileB);
        });
    }

    /**
     * Tạo phản hồi cho thông điệp tham gia phòng.
     * Trong lớp này, phương thức trả về null vì không có phản hồi được tạo.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @return null
     */
    @Override
    protected Message generateResponse(String decryptedContent) {
        return null;
    }

    /**
     * Phân tích đối tượng JsonObject để tạo đối tượng Profile.
     *
     * @param jsonObject Đối tượng Json chứa thông tin người chơi
     * @param sign Ký hiệu để xác định người chơi (A hoặc B)
     * @return Đối tượng Profile được tạo từ thông tin trong jsonObject
     */
    private Profile getProfile(JsonObject jsonObject, String sign) {
        String username = jsonObject.get("username" + sign).getAsString();
        String name = jsonObject.get("name" + sign).getAsString();
        int score = jsonObject.get("score" + sign).getAsInt();
        int matches = jsonObject.get("matches" + sign).getAsInt();
        int win = jsonObject.get("win" + sign).getAsInt();
        int lose = jsonObject.get("lose" + sign).getAsInt();
        int draw = jsonObject.get("draw" + sign).getAsInt();

        return new Profile(username, name, score, matches, win, lose, draw);
    }
}
