package com.example.xogame.notification;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Lớp UnknownPopup đại diện cho một thông báo popup với nội dung không xác định.
 * Lớp này kế thừa từ Popup và cho phép hiển thị thông điệp tùy chỉnh.
 */
public class UnknownPopup extends Popup {
    private Runnable runnable;

    /**
     * Khởi tạo một thể hiện của lớp UnknownPopup.
     *
     * @param message Thông điệp cần hiển thị trong popup.
     * @param runnable Đối tượng Runnable sẽ được thực thi khi popup đóng.
     */
    public UnknownPopup(String message, Runnable runnable) {
        super();
        this.content.setText(message);
        this.runnable = runnable;
    }

    /**
     * Xử lý sự kiện khi người dùng nhấn nút đóng popup.
     *
     * @param event Sự kiện chuột khi người dùng nhấn để đóng popup
     */
    @FXML
    void close(MouseEvent event) {
        runnable.run();
    }
}
