package com.example.xogame.containers;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.controller.AppView;
import com.example.xogame.notification.ConnectionError;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Lớp LoginContainer quản lý giao diện đăng nhập cho người dùng,
 * bao gồm việc xác thực thông tin đăng nhập và điều hướng đến trang đăng ký.
 */
public class LoginContainer extends VBox {
    private AppView appView;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Khởi tạo LoginContainer và tải FXML tương ứng.
     *
     * @param appView Tham chiếu đến AppView của ứng dụng.
     */
    public LoginContainer(AppView appView) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/login.fxml"));
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
     * Xử lý sự kiện khi người dùng nhấn nút đăng nhập.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    private void clickLogin(ActionEvent event) {
        if (username.getText().isEmpty() || password.getText().isEmpty()) return;

        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();

            // Thêm các cặp key-value vào đối tượng JSON
            jsonObject.addProperty("username", username.getText());
            jsonObject.addProperty("password", password.getText());

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("login", contentMessage, "OK");
            ClientSender.send(message);

            // Xóa password đã điền
            password.clear();
        } catch (IOException e) {
            ConnectionError.show((Pane) this.getParent());
        }
    }

    /**
     * Xử lý sự kiện khi người dùng nhấn nút đăng ký.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    private void clickSignup(ActionEvent event) {
        this.appView.showSignup();
    }
}
