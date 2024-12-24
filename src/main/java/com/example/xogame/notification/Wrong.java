package com.example.xogame.notification;

import javafx.scene.layout.Pane;

/**
 * Lớp Wrong đại diện cho một thông báo lỗi khi có điều gì đó không đúng.
 * Lớp này kế thừa từ Error và cho phép hiển thị thông điệp lỗi cụ thể.
 */
public class Wrong extends Error {
    private static Wrong instance;

    /**
     * Khởi tạo một thể hiện của lớp Wrong.
     *
     * @param parent Pane cha mà thông báo lỗi sẽ được thêm vào.
     * @param message Thông điệp lỗi sẽ được hiển thị.
     */
    private Wrong(Pane parent, String message) {
        super(parent);
        this.message.setText(message);
    }

    /**
     * Hiển thị thông báo lỗi.
     *
     * @param parent Pane cha mà thông báo lỗi sẽ được thêm vào.
     * @param message Thông điệp lỗi sẽ được hiển thị.
     * @return Thể hiện của lớp Wrong.
     */
    public static Wrong show(Pane parent, String message) {
        if (instance == null) {
            instance = new Wrong(parent, message);
        }
        instance.message.setText(message);
        if (!parent.getChildren().contains(instance)) {
            instance.parent = parent;
            addToPane(parent, instance);
        }
        return instance;
    }
}
