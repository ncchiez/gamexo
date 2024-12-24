package com.example.xogame.controller;

import com.example.xogame.notification.Popup;
import com.example.xogame.controls.SquareControl;
import com.example.xogame.data.account.Profile;
import com.example.xogame.data.match.Move;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lớp GamePlay đại diện cho một trò chơi, cung cấp các phương thức và thuộc tính cần thiết để quản lý trò chơi.
 * Các trò chơi sẽ mở rộng lớp này để thực hiện các chức năng cụ thể như chọn kích thước quân cờ, gửi thông điệp, và kết thúc trò chơi.
 */
abstract public class GamePlay {
    protected AppView appView;
    protected boolean isGameOver;
    protected int step;
    protected int size;
    protected int opponentSize;
    protected Button selected;
    protected Button opponentSelected;
    protected Profile profile;
    protected Profile opponentProfile;

    @FXML
    protected GridPane board;

    @FXML
    protected Button btn;

    @FXML
    protected AnchorPane content;

    @FXML
    protected Label lose;

    @FXML
    protected Label matches;

    @FXML
    protected Label name;

    @FXML
    protected Label numberOfLarge;

    @FXML
    protected Label numberOfMedium;

    @FXML
    protected Label numberOfSmall;

    @FXML
    protected Button opponentLargeSize;

    @FXML
    protected Label opponentLose;

    @FXML
    protected Label opponentMatches;

    @FXML
    protected Button opponentMediumSize;

    @FXML
    protected Label opponentName;

    @FXML
    protected Label opponentNumberOfLarge;

    @FXML
    protected Label opponentNumberOfMedium;

    @FXML
    protected Label opponentNumberOfSmall;

    @FXML
    protected Label opponentScore;

    @FXML
    protected Button opponentSmallSize;

    @FXML
    protected Label opponentTimeRemainingLabel;

    @FXML
    protected Label opponentWin;

    @FXML
    protected Label score;

    @FXML
    protected VBox selectionBox;

    @FXML
    protected VBox opponentSelectionBox;

    @FXML
    protected Button smallSize;

    @FXML
    protected Label timeRemainingLabel;

    @FXML
    protected Label titleHeader;

    @FXML
    protected Label win;

    @FXML
    protected HBox windowHeaderLeft;

    @FXML
    protected HBox windowHeaderRight;

    /**
     * Phương thức trừu tượng để cho phép đối thủ chọn kích thước quân cờ.
     *
     * @param event Sự kiện ActionEvent khi người chơi chọn kích thước.
     */
    @FXML
    abstract void opponentSelectSize(ActionEvent event);

    // Getter và setter cho các thuộc tính khác nhau

    public AppView getAppView() {
        return appView;
    }

    public void setAppView(AppView appView) {
        this.appView = appView;
    }

    public Stage getStage() {
        return (Stage) name.getScene().getWindow();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOpponentSize() {
        return opponentSize;
    }

    public void setOpponentSize(int opponentSize) {
        this.opponentSize = opponentSize;
    }

    public Button getSelected() {
        return selected;
    }

    public void setSelected(Button selected) {
        this.selected = selected;
    }

    public Button getOpponentSelected() {
        return opponentSelected;
    }

    public void setOpponentSelected(Button opponentSelected) {
        this.opponentSelected = opponentSelected;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        _setProfile(profile, name, score, matches, win, lose);
    }

    public Profile getOpponentProfile() {
        return opponentProfile;
    }

    public void setOpponentProfile(Profile opponentProfile) {
        this.opponentProfile = opponentProfile;
        _setProfile(opponentProfile, opponentName, opponentScore, opponentMatches, opponentWin, opponentLose);
    }

    public Button getBtn() {
        return btn;
    }

    /**
     * Đóng ứng dụng.
     *
     * @param event Sự kiện ActionEvent khi người dùng chọn đóng ứng dụng.
     */
    @FXML
    void exitApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Thu nhỏ cửa sổ ứng dụng.
     *
     * @param event Sự kiện ActionEvent khi người dùng chọn thu nhỏ ứng dụng.
     */
    @FXML
    void minimizeApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Chọn kích thước quân cờ.
     *
     * @param event Sự kiện ActionEvent khi người chơi chọn kích thước.
     */
    @FXML
    void selectSize(ActionEvent event) {
        if (!(event.getSource() instanceof Button button)) return;

        if (selected != null) {
            selected.getStyleClass().clear();
            selected.getStyleClass().add("selection");
        } else {
            smallSize.getStyleClass().clear();
            smallSize.getStyleClass().add("selection");
        }
        selected = button;
        selected.getStyleClass().add("selected");

        HBox parent = (HBox) button.getParent();
        this.size = selectionBox.getChildren().indexOf(parent);
    }

    /**
     * Thực hiện hành động khi nút được nhấn.
     *
     * @param event Sự kiện ActionEvent khi nút được nhấn.
     */
    @FXML
    void btnAction(ActionEvent event) {
        if (this.isGameOver) {
            while (content.getChildren().size() > 6) {
                content.getChildren().removeLast();
            }
            backHome();
            return;
        }

        sendResign();
    }

    /**
     * Quay trở về trang chính.
     */
    protected void backHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/view/app-view.fxml"));
            Scene sceneApp = new Scene(fxmlLoader.load());
            sceneApp.setFill(Color.TRANSPARENT);
            AppView appViewController = fxmlLoader.getController();

            appViewController.setClientListener(this.appView.getClientListener());
            appViewController.setAccount(this.appView.getAccount());
            appViewController.setProfile(this.appView.getProfile());
            appViewController.setToken(this.appView.getToken());

            if (appView.getClientListener() != null) {
                appView.getClientListener().setAppViewController(appViewController);
            }

            appViewController.showHome();
            getStage().setScene(sceneApp);
            appViewController.run();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Phương thức trừu tượng để gửi thông báo từ người chơi.
     */
    abstract void sendResign();

    /**
     * Chạy ứng dụng game.
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
     * Bắt đầu trò chơi.
     */
    public void startGame() {
        this.isGameOver = false;
        this.size = 0;
        this.step = 1;
        this.btn.setText("Resign");
        board.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                SquareControl squareControl = new SquareControl(this, i, j);
                board.add(squareControl, j, i);
            }
        }
    }

    /**
     * Cập nhật thông tin của người chơi.
     *
     * @param profile   Hồ sơ người chơi.
     * @param name      Nhãn tên.
     * @param score     Nhãn điểm số.
     * @param matches   Nhãn số trận.
     * @param win       Nhãn số trận thắng.
     * @param lose      Nhãn số trận thua.
     */
    private void _setProfile(Profile profile, Label name, Label score, Label matches, Label win, Label lose) {
        name.setText(profile.getName());
        score.setText(String.valueOf(profile.getScore()));
        matches.setText(String.valueOf(profile.getMatches()));
        win.setText(String.valueOf(profile.getWin()));
        lose.setText(String.valueOf(profile.getLose()));
    }

    /**
     * Thiết lập số lượng quân cờ cho người chơi và đối thủ.
     *
     * @param s  Số lượng quân nhỏ của người chơi.
     * @param m  Số lượng quân trung của người chơi.
     * @param l  Số lượng quân lớn của người chơi.
     * @param oS Số lượng quân nhỏ của đối thủ.
     * @param oM Số lượng quân trung của đối thủ.
     * @param oL Số lượng quân lớn của đối thủ.
     */
    public void setNumberOfSize(int s, int m, int l, int oS, int oM, int oL) {
        numberOfSmall.setText("x" + s);
        numberOfMedium.setText("x" + m);
        numberOfLarge.setText("x" + l);
        opponentNumberOfSmall.setText("x" + oS);
        opponentNumberOfMedium.setText("x" + oM);
        opponentNumberOfLarge.setText("x" + oL);
    }

    /**
     * Thực hiện một bước đi trong trò chơi.
     *
     * @param move      Đối tượng Move chứa thông tin di chuyển.
     * @param timeLimit Thời gian giới hạn cho lượt đi.
     */
    public void move(Move move, int timeLimit) {
        // Lấy thông tin ô cờ
        SquareControl squareControl = getSquare(board, move.getRow(), move.getCol());
        if (squareControl == null) return;

        // Xóa các styleClass hiện tại
        squareControl.getStyleClass().clear();

        // Lựa chọn styleClass
        boolean myTurn = move.getUsername().equals(profile.getUsername());
        String colorStyleClass = myTurn ? "blue" : "red";
        String sizeStyleClass = getColorStyleClass(move.getSize());

        // Thêm styleClass
        squareControl.getStyleClass().add("square");
        squareControl.getStyleClass().add(colorStyleClass);
        squareControl.getStyleClass().add(sizeStyleClass);

        // Set text
        squareControl.setText(move.getUsername().equals(profile.getUsername()) ? "X" : "O");

        // Update số lượng dấu (quân)
        updateNumberOfSize(move.getSize(), myTurn);

        // Đếm ngược cho lượt kế tiếp
        countDown(timeLimit, !myTurn);
    }

    private SquareControl getSquare(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof SquareControl && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (SquareControl) node;
            }
        }
        return null;
    }

    private String getColorStyleClass(int size) {
        if (size == 0) return "small";
        if (size == 1) return "medium";
        return "large";
    }

    private void updateNumberOfSize(int size, boolean myTurn) {
        Label label = null;

        if (myTurn) {
            switch (size) {
                case 0 -> label = numberOfSmall;
                case 1 -> label = numberOfMedium;
                case 2 -> label = numberOfLarge;
            }
        } else {
            switch (size) {
                case 0 -> label = opponentNumberOfSmall;
                case 1 -> label = opponentNumberOfMedium;
                case 2 -> label = opponentNumberOfLarge;
            }
        }

        if (label == null) return;
        int number = Integer.parseInt(label.getText().substring(1));
        label.setText("x" + (number - 1));
    }

    /**
     * Đếm ngược thời gian cho lượt chơi.
     *
     * @param time     Thời gian bắt đầu đếm ngược.
     * @param myTurn   True nếu là lượt của người chơi, false nếu là lượt của đối thủ.
     */
    public void countDown(int time, boolean myTurn) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        Label label = myTurn ? timeRemainingLabel : opponentTimeRemainingLabel;
        Label hiddenLabel = myTurn ? opponentTimeRemainingLabel : timeRemainingLabel;

        label.setText("It's turn");
        hiddenLabel.setText("");
        AtomicInteger timeRemaining = new AtomicInteger(time);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            if (timeRemaining.get() > 0 && !label.getText().isEmpty() && !this.isGameOver) {
                timeRemaining.getAndDecrement();
                label.setText("Time Remaining: " + timeRemaining + "s");
            } else {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * Phương thức trừu tượng để kết thúc trò chơi.
     *
     * @param winnerUsername Tên người thắng cuộc.
     * @param score         Điểm số của người thắng cuộc.
     */
    abstract public void endGame(String winnerUsername, int score);

    /**
     * Thêm popup vào giao diện.
     *
     * @param popup Popup cần thêm vào.
     */
    public void addPopup(Popup popup) {
        content.getChildren().addLast(popup);

        popup.setLayoutX((content.getWidth() - popup.getPrefWidth()) / 2);
        popup.setLayoutY((content.getHeight() - popup.getPrefHeight()) / 2);
    }

    /**
     * Xóa popup khỏi giao diện.
     */
    public void removePopup() {
        content.getChildren().removeLast();
    }
}
