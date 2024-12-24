package com.example.xogame.containers;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.controller.AppView;
import com.example.xogame.data.account.Profile;
import com.example.xogame.notification.ConnectionError;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Lớp HomeContainer hiển thị giao diện chính cho người dùng,
 * cho phép người dùng tạo phòng, tham gia phòng và tìm kiếm trận đấu.
 */
public class HomeContainer extends AnchorPane {
    private final AppView appView;

    @FXML
    private Label draw;

    @FXML
    private TextField idJoin;

    @FXML
    private Label lose;

    @FXML
    private Label matches;

    @FXML
    private Label name;

    @FXML
    private PasswordField passCreate;

    @FXML
    private PasswordField passJoin;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label ratioDraw;

    @FXML
    private Label ratioLose;

    @FXML
    private Label ratioWin;

    @FXML
    private Label score;

    @FXML
    private Label username;

    @FXML
    private Label win;

    /**
     * Khởi tạo HomeContainer và tải FXML tương ứng.
     *
     * @param appView Tham chiếu đến AppView của ứng dụng.
     */
    public HomeContainer(AppView appView) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/containers/home.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.appView = appView;
        showData(appView.getProfile());
    }

    /**
     * Hiển thị thông tin người dùng trong giao diện.
     *
     * @param profile Thông tin người dùng.
     */
    private void showData(Profile profile) {
        if (profile == null) return;

        // show information
        name.setText(profile.getName());
        username.setText(profile.getUsername());
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

        // draw chart
        pieChart.getData().clear();
        pieChart.getData().addAll(
                new PieChart.Data("Win", profile.getWin()),
                new PieChart.Data("Lose", profile.getLose()),
                new PieChart.Data("Draw", profile.getDraw())
        );
    }

    /**
     * Xử lý sự kiện khi người dùng tạo phòng.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void createRoom(ActionEvent event) {
        String password = passCreate.getText();

        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("password", password);

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("create_room", contentMessage, "OK");
            ClientSender.send(message);

        } catch (IOException e) {
            appView.logout();
            ConnectionError.show(appView.getContent());
        }
    }

    /**
     * Xử lý sự kiện khi người dùng tham gia phòng.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void joinRoom(ActionEvent event) {
        String idRoom = idJoin.getText();
        String password = passJoin.getText();

        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("idRoom", idRoom);
            jsonObject.addProperty("password", password);

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("join_room", contentMessage, "OK");
            ClientSender.send(message);

        } catch (IOException e) {
            appView.logout();
            ConnectionError.show(appView.getContent());
        }
    }

    /**
     * Xử lý sự kiện khi người dùng tìm kiếm trận đấu.
     *
     * @param event Sự kiện nhấp chuột.
     */
    @FXML
    void findMatch(ActionEvent event) {
        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("find_match", contentMessage, "OK");
            ClientSender.send(message);

            // Chuyển cảnh đợi
            appView.showFinding();
        } catch (IOException e) {
            appView.logout();
            ConnectionError.show(appView.getContent());
        }
    }
}
