package com.example.xogame.containers;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.notification.ConnectionError;
import com.example.xogame.notification.Wrong;
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
 * Lớp SignupContainer quản lý giao diện đăng ký tài khoản người dùng.
 */
public class SignupContainer extends VBox {

    @FXML
    private PasswordField comfirmPassword;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    /**
     * Khởi tạo SignupContainer và tải FXML tương ứng.
     */
    public SignupContainer() {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/signup.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Xử lý sự kiện nhấp chuột để tạo tài khoản mới.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void clickCreateAccount(ActionEvent event) {
        if (username.getText().isEmpty() || password.getText().isEmpty() || comfirmPassword.getText().isEmpty()
                || name.getText().isEmpty() || email.getText().isEmpty()) {
            Wrong.show((Pane) this.getParent(), "Chưa nhập đủ thông tin!");
            return;
        }

        if (!password.getText().equals(comfirmPassword.getText())) {
            Wrong.show((Pane) this.getParent(), "Mật khẩu nhập lại đã bị sai.");
            comfirmPassword.clear();
            return;
        }

        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();

            // Thêm các cặp key-value vào đối tượng JSON
            jsonObject.addProperty("username", username.getText());
            jsonObject.addProperty("password", password.getText());
            jsonObject.addProperty("name", name.getText());
            jsonObject.addProperty("email", email.getText());

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("signup", contentMessage, "OK");
            ClientSender.send(message);

        } catch (IOException e) {
            ConnectionError.show((Pane) this.getParent());
        }
    }
}
