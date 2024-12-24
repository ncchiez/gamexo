package com.example.xogame.controller;

import com.example.xogame.ClientListener;
import com.example.xogame.containers.*;
import com.example.xogame.data.account.Account;
import com.example.xogame.data.account.EmptyProfile;
import com.example.xogame.data.account.Profile;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp AppView đại diện cho giao diện chính của ứng dụng trò chơi.
 * Nó quản lý các thành phần giao diện và điều hướng giữa các màn hình khác nhau.
 */
public class AppView {
    private ClientListener clientListener;
    private String token;
    private Account account;
    private Profile profile;

    @FXML
    private Pane content;

    @FXML
    private Label titleHeader;

    @FXML
    private AnchorPane window;

    @FXML
    private HBox windowHeaderLeft;

    @FXML
    private HBox windowHeaderRight;

    /**
     * Khởi động ứng dụng và cho phép kéo cửa sổ.
     */
    public void run() {
        Stage stage = (Stage) windowHeaderLeft.getScene().getWindow();
        windowHeaderLeft.setOnMousePressed(pressEvent -> {
            windowHeaderLeft.setOnMouseDragged(dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        windowHeaderRight.setOnMousePressed(pressEvent -> {
            windowHeaderRight.setOnMouseDragged(dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }

    /**
     * Xóa tất cả dữ liệu người dùng.
     */
    public void clear() {
        this.token = null;
        this.account = null;
        this.profile = null;
    }

    /**
     * Đóng ứng dụng.
     *
     * @param event Sự kiện nhấn nút thoát.
     */
    @FXML
    void exitApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Thu nhỏ cửa sổ ứng dụng.
     *
     * @param event Sự kiện nhấn nút thu nhỏ.
     */
    @FXML
    void minimizeApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Bắt đầu một trò chơi với máy tính.
     *
     * @param event Sự kiện nhấn nút chơi với máy tính.
     */
    @FXML
    void playComputer(ActionEvent event) {
        if (isBusy()) return;

        SelectCOM selectCOM = new SelectCOM(this);
        window.getChildren().add(selectCOM);

        selectCOM.setLayoutX((window.getPrefWidth() - selectCOM.getPrefWidth()) / 2);
        selectCOM.setLayoutY((window.getPrefHeight() - selectCOM.getPrefHeight()) / 2);
    }

    /**
     * Bắt đầu một trò chơi trực tuyến.
     *
     * @param event Sự kiện nhấn nút chơi trực tuyến.
     */
    @FXML
    void playOnline(ActionEvent event) {
        if (isBusy()) return;

        if (profile == null || token == null) {
            showLogin();
        } else {
            showHome();
        }
    }

    /**
     * Bắt đầu một trò chơi cá nhân.
     *
     * @param event Sự kiện nhấn nút chơi một mình.
     */
    @FXML
    void playYourself(ActionEvent event) {
        if (isBusy()) return;

        // Tạo gameplay
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/view/game-yourself.fxml"));
        try {
            Scene sceneGamePlay = new Scene(fxmlLoader.load());
            sceneGamePlay.setFill(Color.TRANSPARENT);
            GameYourself gameYourself = fxmlLoader.getController();
            gameYourself.setAppView(this);

            // Thiết lập dữ liệu khởi tạo
            gameYourself.setProfile(new EmptyProfile("1"));
            gameYourself.setOpponentProfile(new EmptyProfile("2"));
            gameYourself.startGame();

            // Cấu hình Stage với Scene và hiển thị nó
            Stage stage = (Stage) this.getContent().getScene().getWindow();
            stage.setTitle("Play!");
            stage.setScene(sceneGamePlay);
            gameYourself.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hiển thị thông tin về ứng dụng.
     *
     * @param event Sự kiện nhấn nút "About".
     */
    @FXML
    void showAbout(ActionEvent event) {
        if (isBusy()) return;

        titleHeader.setText("About");
        content.getChildren().clear();

        AboutContainer aboutContainer = new AboutContainer(this);
        content.getChildren().add(aboutContainer);
    }

    /**
     * Hiển thị thông tin cá nhân.
     *
     * @param event Sự kiện nhấn nút "Information".
     */
    @FXML
    void showInfor(ActionEvent event) {
        if (isBusy()) return;

        titleHeader.setText("Information");
        content.getChildren().clear();
    }

    /**
     * Kiểm tra xem ứng dụng có đang bận hay không.
     *
     * @return true nếu đang bận, false nếu không.
     */
    private boolean isBusy() {
        if (content.getChildren().isEmpty()) return false;
        if (window.getChildren().getLast() instanceof SelectCOM) return true;
        return content.getChildren().getFirst() instanceof FindingContainer;
    }

    /**
     * Hiển thị màn hình đăng nhập.
     */
    public void showLogin() {
        titleHeader.setText("Login");
        content.getChildren().clear();

        LoginContainer loginContainer = new LoginContainer(this);
        content.getChildren().add(loginContainer);

        loginContainer.setLayoutX((content.getPrefWidth() - loginContainer.getPrefWidth()) / 2);
        loginContainer.setLayoutY((content.getPrefHeight() - loginContainer.getPrefHeight()) / 2);
    }

    /**
     * Hiển thị màn hình đăng ký.
     */
    public void showSignup() {
        titleHeader.setText("Sign up");
        content.getChildren().clear();

        SignupContainer signupContainer = new SignupContainer();
        content.getChildren().add(signupContainer);

        signupContainer.setLayoutX((content.getPrefWidth() - signupContainer.getPrefWidth()) / 2);
        signupContainer.setLayoutY((content.getPrefHeight() - signupContainer.getPrefHeight()) / 2);
    }

    /**
     * Hiển thị màn hình chính sau khi đăng nhập.
     */
    public void showHome() {
        if (profile == null || token == null) return;

        titleHeader.setText("Home");
        content.getChildren().clear();

        HomeContainer homeContainer = new HomeContainer(this);
        content.getChildren().add(homeContainer);
    }

    /**
     * Hiển thị màn hình tìm kiếm đối thủ.
     */
    public void showFinding() {
        titleHeader.setText("Finding Match");
        content.getChildren().clear();

        FindingContainer findingContainer = new FindingContainer(this);
        content.getChildren().add(findingContainer);
    }

    /**
     * Hiển thị phòng chơi dựa trên ID phòng.
     *
     * @param idRoom ID của phòng chơi.
     */
    public void showRoom(String idRoom) {
        titleHeader.setText("Room");
        content.getChildren().clear();

        RoomContainer roomContainer = new RoomContainer(this, idRoom);
        content.getChildren().add(roomContainer);
    }

    /**
     * Cập nhật thông tin phòng chơi hiện tại.
     */
    public void updateRoom() {
        if (!(content.getChildren().getFirst() instanceof RoomContainer roomContainer)) return;
        roomContainer.setProfileA(this.profile);
    }

    /**
     * Cập nhật thông tin phòng chơi với đối thủ mới.
     *
     * @param idRoom     ID của phòng chơi.
     * @param profileA   Hồ sơ người chơi A.
     * @param profileB   Hồ sơ người chơi B.
     */
    public void updateRoom(String idRoom, Profile profileA, Profile profileB) {
        if (this.profile.getUsername().equals(profileA.getUsername())) {
            if (!(content.getChildren().getLast() instanceof RoomContainer roomContainer)) return;

            roomContainer.setProfileB(profileB);
            roomContainer.showStartButton();
            return;
        }

        this.showRoom(idRoom);
        if (!(content.getChildren().getFirst() instanceof RoomContainer roomContainer)) return;
        roomContainer.setProfileA(profileA);
        roomContainer.setProfileB(profileB);
    }

    /**
     * Đăng xuất người dùng và trở về màn hình đăng nhập.
     */
    public void logout() {
        clear();
        showLogin();
    }

    public ClientListener getClientListener() {
        return clientListener;
    }

    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }

    public Pane getContent() {
        return content;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HBox getWindowHeaderLeft() {
        return windowHeaderLeft;
    }

    public HBox getWindowHeaderRight() {
        return windowHeaderRight;
    }

    public AnchorPane getWindow() {
        return window;
    }
}
