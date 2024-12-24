package com.example.xogame.notification;

import javafx.scene.layout.Pane;

/**
 * Lớp ConnectionError đại diện cho thông báo lỗi kết nối trong ứng dụng.
 * Lớp này sử dụng mẫu thiết kế Singleton để đảm bảo chỉ có một thể hiện duy nhất
 * của thông báo lỗi này trong suốt vòng đời ứng dụng.
 */
public class ConnectionError extends Error {
    private static ConnectionError instance;

    /**
     * Khởi tạo một thể hiện của ConnectionError với thông điệp lỗi.
     *
     * @param parent Pane cha mà thông báo lỗi sẽ được hiển thị
     */
    private ConnectionError(Pane parent) {
        super(parent);
        this.message.setText("Lỗi kết nối!");
    }

    /**
     * Hiển thị thông báo lỗi kết nối.
     * Nếu thông báo lỗi đã được tạo, nó sẽ được hiển thị lại.
     *
     * @param parent Pane cha mà thông báo lỗi sẽ được hiển thị
     * @return Thể hiện của ConnectionError
     */
    public static ConnectionError show(Pane parent) {
        if (instance == null) {
            instance = new ConnectionError(parent);
        }
        if (!parent.getChildren().contains(instance)) {
            instance.parent = parent;
            addToPane(parent, instance);
        }
        return instance;
    }
}
