package com.example.xogame.containers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Lớp SelectLevel quản lý giao diện chọn cấp độ cho trò chơi.
 */
public class SelectLevel extends HBox {
    private int selectedLevel = -1;

    /**
     * Khởi tạo SelectLevel và tải FXML tương ứng.
     */
    public SelectLevel() {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/select-level.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Xử lý sự kiện nhấp chuột để chọn cấp độ.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void click(ActionEvent event) {
        for (Node node : this.getChildren()) {
            if (!(node instanceof Button button)) continue;

            button.getStyleClass().clear();
            button.getStyleClass().add("selection");
        }

        if (!(event.getSource() instanceof Button button)) return;
        button.getStyleClass().clear();
        button.getStyleClass().add("selected");
        selectedLevel = this.getChildren().indexOf(button);
    }

    /**
     * Lấy cấp độ đã chọn.
     *
     * @return Cấp độ đã chọn, hoặc -1 nếu không có cấp độ nào được chọn.
     */
    public int getSelectedLevel() {
        return selectedLevel;
    }
}
