package com.example.xogame.containers;

import com.example.xogame.controller.AppView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp AboutContainer hiển thị thông tin về ứng dụng,
 * bao gồm liên hệ qua email và các liên kết mạng xã hội.
 */
public class AboutContainer extends AnchorPane {
    @FXML
    private Label email;
    @FXML
    private Hyperlink facebookContactLink;

    /**
     * Khởi tạo AboutContainer và tải FXML tương ứng.
     *
     * @param appView Tham chiếu đến AppView của ứng dụng.
     */
    public AboutContainer(AppView appView) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/about.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Xử lý sự kiện khi người dùng nhấp vào liên kết Facebook.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void clickFacebookContact(ActionEvent event) {
        Application application = new Application() {
            @Override
            public void start(Stage stage) throws Exception {
                getHostServices().showDocument(facebookContactLink.getText());
            }
        };
        try {
            Stage stage = new Stage();
            application.start(stage);
            stage.close();
            application.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sao chép địa chỉ email vào clipboard khi người dùng nhấp vào.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void copyEmailContact(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email.getText());
        clipboard.setContent(content);
    }
}
