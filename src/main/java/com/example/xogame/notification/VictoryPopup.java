package com.example.xogame.notification;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * Lớp VictoryPopup đại diện cho một thông báo popup khi người chơi chiến thắng.
 * Lớp này kế thừa từ Popup và cho phép hiển thị thông điệp chiến thắng cùng với hình ảnh.
 */
public class VictoryPopup extends Popup {
    private Runnable runnable;

    /**
     * Khởi tạo một thể hiện của lớp VictoryPopup.
     *
     * @param score Số điểm người chơi nhận được khi chiến thắng.
     * @param runnable Đối tượng Runnable sẽ được thực thi khi popup đóng.
     */
    public VictoryPopup(int score, Runnable runnable) {
        super();
        this.imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/xogame/gif/win.gif"))));
        this.content.setText("Bạn đã chiến thắng! Bạn được cộng " + score + " điểm.");
        this.runnable = runnable;
    }

    /**
     * Xử lý sự kiện khi người dùng nhấn nút đóng popup.
     *
     * @param event Sự kiện chuột khi người dùng nhấn để đóng popup.
     */
    @FXML
    void close(MouseEvent event) {
        runnable.run();
    }
}
