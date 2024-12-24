package com.example.xogame.containers;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.controller.AppView;
import com.example.xogame.data.account.Profile;
import com.example.xogame.notification.ConnectionError;
import com.example.xogame.notification.Wrong;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Lớp RoomContainer quản lý giao diện phòng chơi,
 * bao gồm việc hiển thị thông tin người chơi, bắt đầu trò chơi,
 * và sao chép ID phòng.
 */
public class RoomContainer extends AnchorPane {
    private final AppView appView;

    @FXML
    private Label draw;

    @FXML
    private Label drawB;

    @FXML
    private Label idRoom;

    @FXML
    private Label lose;

    @FXML
    private Label loseB;

    @FXML
    private Label matches;

    @FXML
    private Label matchesB;

    @FXML
    private Label name;

    @FXML
    private Label nameB;

    @FXML
    private Label ratioDraw;

    @FXML
    private Label ratioDrawB;

    @FXML
    private Label ratioLose;

    @FXML
    private Label ratioLoseB;

    @FXML
    private Label ratioWin;

    @FXML
    private Label ratioWinB;

    @FXML
    private Label score;

    @FXML
    private Label scoreB;

    @FXML
    private Button startButton;

    @FXML
    private Label username;

    @FXML
    private Label usernameB;

    @FXML
    private Label win;

    @FXML
    private Label winB;

    /**
     * Khởi tạo RoomContainer và tải FXML tương ứng.
     *
     * @param appView Tham chiếu đến AppView của ứng dụng.
     * @param idRoom ID của phòng chơi.
     */
    public RoomContainer(AppView appView, String idRoom) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/room.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.startButton.setDisable(true);
        this.appView = appView;
        this.idRoom.setText(idRoom);
    }

    /**
     * Thiết lập thông tin người chơi A.
     *
     * @param profileA Thông tin người chơi A.
     */
    public void setProfileA(Profile profileA) {
        _setProfile(profileA, username, name, score, matches, win, lose, draw, ratioWin, ratioLose, ratioDraw);
    }

    /**
     * Xóa thông tin người chơi A.
     */
    public void removeProfileA() {
        _removeProfile(username, name, score, matches, win, lose, draw, ratioWin, ratioLose, ratioDraw);
    }

    /**
     * Thiết lập thông tin người chơi B.
     *
     * @param profileB Thông tin người chơi B.
     */
    public void setProfileB(Profile profileB) {
        _setProfile(profileB, usernameB, nameB, scoreB, matchesB, winB, loseB, drawB, ratioWinB, ratioLoseB, ratioDrawB);
    }

    /**
     * Xóa thông tin người chơi B.
     */
    public void removeProfileB() {
        _removeProfile(usernameB, nameB, scoreB, matchesB, winB, loseB, drawB, ratioWinB, ratioLoseB, ratioDrawB);
    }

    private void _setProfile(Profile profile, Label username, Label name, Label score, Label matches, Label win, Label lose, Label draw, Label ratioWin, Label ratioLose, Label ratioDraw) {
        username.setText(profile.getUsername());
        name.setText(profile.getName());
        score.setText(String.valueOf(profile.getScore()));
        matches.setText(String.valueOf(profile.getMatches()));
        win.setText(String.valueOf(profile.getWin()));
        lose.setText(String.valueOf(profile.getLose()));
        draw.setText(String.valueOf(profile.getDraw()));

        if (profile.getMatches() != 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            ratioWin.setText(decimalFormat.format(profile.getWin() * 100.0 / profile.getMatches()));
            ratioLose.setText(decimalFormat.format(profile.getLose() * 100.0 / profile.getMatches()));
            ratioDraw.setText(decimalFormat.format(profile.getDraw() * 100.0 / profile.getMatches()));
        }
    }

    private void _removeProfile(Label username, Label name, Label score, Label matches, Label win, Label lose, Label draw, Label ratioWin, Label ratioLose, Label ratioDraw) {
        username.setText("");
        name.setText("");
        score.setText("");
        matches.setText("");
        win.setText("");
        lose.setText("");
        draw.setText("");
        ratioWin.setText("");
        ratioLose.setText("");
        ratioDraw.setText("");
    }

    /**
     * Lấy tên người dùng của người chơi A.
     *
     * @return Tên người dùng hoặc null nếu không có.
     */
    public String getUsernameA() {
        if (username.getText().isEmpty()) return null;
        return username.getText();
    }

    /**
     * Lấy tên người dùng của người chơi B.
     *
     * @return Tên người dùng hoặc null nếu không có.
     */
    public String getUsernameB() {
        if (usernameB.getText().isEmpty()) return null;
        return usernameB.getText();
    }

    /**
     * Hiện nút bắt đầu trò chơi.
     */
    public void showStartButton() {
        this.startButton.setDisable(false);
    }

    /**
     * Sao chép ID phòng vào clipboard.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void copyID(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(idRoom.getText());
        clipboard.setContent(content);
    }

    /**
     * Rời khỏi phòng chơi.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void exitRoom(ActionEvent event) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("idRoom", idRoom.getText());
            String contentMessage = jsonObject.toString();

            ClientSender.send(new Message("exit_room", contentMessage, "OK"));
        } catch (IOException e) {
            appView.logout();
            Wrong.show(appView.getWindow(), "Something wrongs.");
        }
    }

    /**
     * Bắt đầu trò chơi.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void startGame(ActionEvent event) {
        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();

            // Tạo nội dung thông điệp
            jsonObject.addProperty("idRoom", idRoom.getText());
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("start_game", contentMessage, "OK");
            ClientSender.send(message);

        } catch (IOException e) {
            appView.logout();
            ConnectionError.show(appView.getContent());
        }
    }
}
