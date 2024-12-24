package com.example.xogame.notification;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * Lớp DrawnPopup đại diện cho thông báo popup hiển thị khi trận đấu hòa.
 * Popup này cung cấp thông tin về số điểm được cộng và có thể thực thi một hành động sau khi đóng.
 */
public class DrawnPopup extends Popup {
    private final Runnable runnable;

    /**
     * Khởi tạo một thể hiện của DrawnPopup với số điểm được cộng và hành động cần thực hiện khi đóng popup.
     *
     * @param score   Số điểm được cộng khi trận đấu hòa
     * @param runnable Hành động cần thực hiện sau khi đóng popup
     */
    public DrawnPopup(int score, Runnable runnable) {
        super();
        this.imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/xogame/gif/draw.gif"))));
        this.content.setText("Trận đấu hòa! Bạn được cộng " + score + " điểm.");
        this.runnable = runnable;
    }

    /**
     * Xử lý sự kiện khi người dùng đóng popup.
     *
     * @param event Sự kiện chuột khi đóng popup
     */
    @FXML
    void close(MouseEvent event) {
        runnable.run();
    }
}
