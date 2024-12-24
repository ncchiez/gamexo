package com.example.xogame;

import com.example.xogame.controller.AppView;
import com.example.xogame.notification.Wrong;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

/**
 * Lớp Client là entry point của ứng dụng Game XO. Nó quản lý giao diện người dùng
 * và kết nối đến server, đồng thời khởi tạo các thành phần giao diện và điều khiển.
 */
public class Client extends Application {

    /**
     * Phương thức main là điểm bắt đầu của ứng dụng, gọi phương thức launch()
     * để khởi chạy JavaFX.
     *
     * @param args Đối số dòng lệnh
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Phương thức start được gọi khi ứng dụng JavaFX khởi chạy. Nó khởi tạo giao diện,
     * kết nối đến server và thiết lập các thành phần điều khiển.
     *
     * @param stage Đối tượng Stage để hiển thị giao diện chính
     * @throws Exception Nếu xảy ra lỗi trong quá trình khởi tạo
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load file FXML để cấu hình giao diện
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/view/app-view.fxml"));
        Scene sceneApp = new Scene(fxmlLoader.load());
        sceneApp.setFill(Color.TRANSPARENT);
        AppView appViewController = fxmlLoader.getController();

        // Cấu hình Stage với Scene và hiển thị nó
        stage.setTitle("GameXO");
        stage.setScene(sceneApp);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);

        appViewController.run();
        appViewController.showLogin();

        // Thiết lập biểu tượng của ứng dụng
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/xogame/img/ic.png"))));
        stage.show();

        try {
            // Kết nối đến server
            Socket socket = new Socket("localhost", 12345);

            // Truyền socket cho ClientSender
            ClientSender.setSocket(socket);

            // Gắn các giá trị cho ClientListener
            ClientListener clientListener = new ClientListener(socket);
            clientListener.setAppViewController(appViewController);

            appViewController.setClientListener(clientListener);

            // Tạo luồng lắng nghe cho ClientListener
            Thread clientListenerThread = new Thread(clientListener);
            clientListenerThread.start();

            // Tạo một sự kiện khi cửa sổ được đóng để đóng kết nối
            stage.setOnCloseRequest(event -> {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            // Hiển thị thông báo lỗi nếu kết nối thất bại
            Wrong.show(appViewController.getContent(), "Connection failed! Check your connection again or switch to offline mode.");
        }
    }
}
