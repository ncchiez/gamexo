package com.example.xogame.containers;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.controller.AppView;
import com.example.xogame.notification.ConnectionError;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Lớp FindingContainer hiển thị giao diện tìm kiếm trận đấu
 * và xử lý sự kiện liên quan đến việc hủy tìm kiếm.
 */
public class FindingContainer extends AnchorPane {
    private final AppView appView;

    /**
     * Khởi tạo FindingContainer và tải FXML tương ứng.
     *
     * @param appView Tham chiếu đến AppView của ứng dụng.
     */
    public FindingContainer(AppView appView) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/waiting.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.appView = appView;
    }

    /**
     * Xử lý sự kiện khi người dùng muốn hủy tìm kiếm trận đấu.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void cancelFinding(ActionEvent event) {
        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();

            // Thêm các cặp key-value vào đối tượng JSON
            jsonObject.addProperty("token", appView.getToken());

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("cancel_finding", contentMessage, "OK");
            ClientSender.send(message);

            // Chuyển về trang chủ
            appView.showHome();
        } catch (IOException e) {
            appView.clear();
            appView.showLogin();
            ConnectionError.show(appView.getContent());
        }
    }
}
