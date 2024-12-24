package com.example.xogame.notification;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * Lớp DefeatPopup đại diện cho thông báo popup hiển thị khi người chơi thua trận.
 * Popup này cung cấp thông tin về số điểm bị trừ và có thể thực thi một hành động sau khi đóng.
 */
public class DefeatPopup extends Popup {
    private final Runnable runnable;

    /**
     * Khởi tạo một thể hiện của DefeatPopup với số điểm bị trừ và hành động cần thực hiện khi đóng popup.
     *
     * @param score   Số điểm bị trừ khi người chơi thua
     * @param runnable Hành động cần thực hiện sau khi đóng popup
     */
    public DefeatPopup(int score, Runnable runnable) {
        super();
        this.imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/xogame/gif/lose.gif"))));
        this.content.setText("Bạn đã thua cuộc! Bạn bị trừ " + score + " điểm.");
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
