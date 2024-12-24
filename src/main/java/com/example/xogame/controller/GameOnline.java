package com.example.xogame.controller;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.notification.DefeatPopup;
import com.example.xogame.notification.DrawnPopup;
import com.example.xogame.notification.Popup;
import com.example.xogame.notification.VictoryPopup;
import com.example.xogame.data.account.Profile;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Lớp GameOnline đại diện cho các trò chơi trực tuyến.
 * Nó mở rộng lớp GamePlay và cung cấp các phương thức để quản lý trò chơi trực tuyến.
 */
public class GameOnline extends GamePlay {
    private String idMatch;
    private String token;

    /**
     * Lấy ID trận đấu.
     *
     * @return ID trận đấu.
     */
    public String getIdMatch() {
        return idMatch;
    }

    /**
     * Thiết lập ID trận đấu.
     *
     * @param idMatch ID trận đấu.
     */
    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    /**
     * Lấy token xác thực.
     *
     * @return token xác thực.
     */
    public String getToken() {
        return token;
    }

    /**
     * Thiết lập token xác thực.
     *
     * @param token token xác thực.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Xóa thông tin token.
     */
    public void clear() {
        this.token = null;
    }

    @Override
    void opponentSelectSize(ActionEvent event) {
        // Phương thức này có thể được triển khai trong các lớp con.
    }

    @Override
    void sendResign() {
        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = new JsonObject();

            // Thêm các cặp key-value vào đối tượng JSON
            jsonObject.addProperty("idMatch", this.idMatch);
            jsonObject.addProperty("token", this.token);

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("resign", contentMessage, "OK");
            ClientSender.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endGame(String winnerUsername, int score) {
        this.setGameOver(true);

        // Đổi chữ button
        this.getBtn().setText("Exit");

        Profile myProfile = this.appView.getProfile();
        if (winnerUsername.equals(this.getProfile().getUsername())) {
            // Thắng
            Popup victoryPopup = new VictoryPopup(score, this::removePopup);
            this.addPopup(victoryPopup);

            myProfile.setScore(myProfile.getScore() + score);
            myProfile.setMatches(myProfile.getMatches() + 1);
            myProfile.setWin(myProfile.getWin() + 1);
        } else if (winnerUsername.equals(this.getOpponentProfile().getUsername())) {
            // Thua
            Popup defeatPopup = new DefeatPopup(score, this::removePopup);
            this.addPopup(defeatPopup);

            myProfile.setScore(myProfile.getScore() + score);
            myProfile.setMatches(myProfile.getMatches() + 1);
            myProfile.setLose(myProfile.getLose() + 1);
        } else {
            // Hòa
            Popup drawnPopup = new DrawnPopup(score, this::removePopup);
            this.addPopup(drawnPopup);

            myProfile.setScore(myProfile.getScore() + score);
            myProfile.setMatches(myProfile.getMatches() + 1);
            myProfile.setDraw(myProfile.getDraw() + 1);
        }
    }
}
