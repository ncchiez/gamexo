package com.example.xogame.containers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Lớp SelectOrder quản lý giao diện chọn thứ tự cho trò chơi.
 */
public class SelectOrder extends HBox {
    private int selectedOrder = -1;

    /**
     * Khởi tạo SelectOrder và tải FXML tương ứng.
     */
    public SelectOrder() {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/select-order.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Xử lý sự kiện nhấp chuột để chọn thứ tự.
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
        selectedOrder = this.getChildren().indexOf(button);
    }

    /**
     * Lấy thứ tự đã chọn.
     *
     * @return Thứ tự đã chọn, hoặc -1 nếu không có thứ tự nào được chọn.
     */
    public int getSelectedOrder() {
        return selectedOrder;
    }
}
