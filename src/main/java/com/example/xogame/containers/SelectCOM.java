package com.example.xogame.containers;

import com.example.xogame.computer.ComputerFactory;
import com.example.xogame.controller.AppView;
import com.example.xogame.controller.GameComputer;
import com.example.xogame.data.account.ComputerProfile;
import com.example.xogame.data.account.EmptyProfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp SelectCOM quản lý giao diện chọn máy tính để chơi,
 * bao gồm việc thiết lập cấp độ và thứ tự chơi.
 */
public class SelectCOM extends VBox {
    private final AppView appView;
    private final SelectLevel selectLevel = new SelectLevel();
    private final SelectOrder selectOrder = new SelectOrder();

    @FXML
    private VBox content;

    /**
     * Khởi tạo SelectCOM và tải FXML tương ứng.
     *
     * @param appView Tham chiếu đến AppView của ứng dụng.
     */
    public SelectCOM(AppView appView) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/computer-selection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.appView = appView;

        while (content.getChildren().size() > 1) {
            content.getChildren().removeFirst();
        }
        content.getChildren().addFirst(selectOrder);
        content.getChildren().addFirst(selectLevel);
    }

    /**
     * Hủy bỏ việc chọn máy tính và đóng giao diện.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void cancel(ActionEvent event) {
        this.appView.getWindow().getChildren().remove(this);
    }

    /**
     * Xác nhận việc chọn máy tính và bắt đầu trò chơi.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void submit(ActionEvent event) {
        if (selectOrder.getSelectedOrder() == -1 || selectLevel.getSelectedLevel() == -1) return;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/view/game-com.fxml"));
        try {
            Scene sceneGamePlay = new Scene(fxmlLoader.load());
            sceneGamePlay.setFill(Color.TRANSPARENT);
            GameComputer gameComputer = fxmlLoader.getController();
            gameComputer.setAppView(appView);

            // Thiết lập dữ liệu khởi tạo
            if (selectOrder.getSelectedOrder() == 0) {
                gameComputer.setProfile(new EmptyProfile("__Player__"));
                gameComputer.setOpponentProfile(new ComputerProfile());
            } else {
                gameComputer.setProfile(new ComputerProfile());
                gameComputer.setOpponentProfile(new EmptyProfile("__Player__"));
            }
            gameComputer.setCom(ComputerFactory.createComputer(selectLevel.getSelectedLevel()));
            gameComputer.startGame();

            // Cấu hình Stage với Scene và hiển thị nó
            Stage stage = (Stage) appView.getContent().getScene().getWindow();
            stage.setTitle("Play!");
            stage.setScene(sceneGamePlay);
            gameComputer.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
