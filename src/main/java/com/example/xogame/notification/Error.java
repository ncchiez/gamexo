package com.example.xogame.notification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Lớp Error đại diện cho một thông báo lỗi trong ứng dụng.
 * Lớp này kế thừa từ AnchorPane và có khả năng hiển thị thông báo lỗi.
 */
public abstract class Error extends AnchorPane {
    protected Pane parent;

    @FXML
    protected Label message;

    /**
     * Xử lý sự kiện khi người dùng nhấn nút thoát.
     *
     * @param event Sự kiện chuột khi người dùng nhấn nút thoát
     */
    @FXML
    void exit(ActionEvent event) {
        parent.getChildren().remove(this);
    }

    /**
     * Khởi tạo một thể hiện của lớp Error với một pane cha.
     *
     * @param parent Pane cha nơi thông báo lỗi sẽ được thêm vào
     */
    public Error(Pane parent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/notification/error.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();

            this.parent = parent;
            addToPane(parent, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Thêm thông báo lỗi vào một pane cha và căn giữa nó.
     *
     * @param parent Pane cha nơi thông báo lỗi sẽ được thêm vào
     * @param error  Thông báo lỗi cần thêm
     */
    protected static void addToPane(Pane parent, Error error) {
        parent.getChildren().add(error);
        error.setLayoutX((parent.getPrefWidth() - error.getPrefWidth()) / 2);
        error.setLayoutY((parent.getPrefHeight() - error.getPrefHeight()) / 2);
    }
}
