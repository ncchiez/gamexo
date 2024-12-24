package com.example.xogame.notification;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp Popup đại diện cho một thông báo popup trong ứng dụng.
 * Lớp này kế thừa từ AnchorPane và cho phép hiển thị nội dung và hình ảnh.
 */
public class Popup extends AnchorPane {
    @FXML
    protected Label content;

    @FXML
    protected ImageView imageView;

    /**
     * Xử lý sự kiện khi người dùng nhấn nút đóng popup.
     *
     * @param event Sự kiện chuột khi người dùng nhấn để đóng popup
     */
    @FXML
    void close(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Khởi tạo một thể hiện của lớp Popup.
     * Tải tệp FXML để cấu hình giao diện cho popup.
     */
    public Popup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/notification/popup.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
