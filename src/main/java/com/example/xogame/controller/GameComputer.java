package com.example.xogame.controller;

import com.example.xogame.computer.Computer;
import com.example.xogame.data.account.ComputerProfile;
import com.example.xogame.data.account.Profile;
import com.example.xogame.data.match.Game;
import com.example.xogame.data.match.Move;
import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * Lớp GameComputer đại diện cho trò chơi với máy tính.
 * Nó mở rộng lớp GameOffline để thêm các chức năng đặc biệt cho trò chơi với máy tính.
 */
public class GameComputer extends GameOffline {
    private Computer com;

    /**
     * Lấy đối tượng máy tính.
     *
     * @return Đối tượng máy tính.
     */
    public Computer getCom() {
        return com;
    }

    /**
     * Thiết lập đối tượng máy tính.
     *
     * @param com Đối tượng máy tính.
     */
    public void setCom(Computer com) {
        this.com = com;
    }

    /**
     * Bắt đầu trò chơi.
     * Gọi hàm startGame của lớp cha và thiết lập trò chơi với người chơi và đối thủ.
     * Nếu lượt của máy tính, thực hiện lượt đi.
     */
    @Override
    public void startGame() {
        super.startGame();
        this.game = new Game(profile.getUsername(), opponentProfile.getUsername());
        this.setNumberOfSize(game.getPocketA().getNumberOfSize(0), game.getPocketA().getNumberOfSize(1),
                game.getPocketA().getNumberOfSize(2), game.getPocketB().getNumberOfSize(0),
                game.getPocketB().getNumberOfSize(1), game.getPocketB().getNumberOfSize(2));
        if (checkTurnComputer()) {
            smallSize.getStyleClass().clear();
            smallSize.getStyleClass().add("selection");
            opponentSmallSize.getStyleClass().add("selected");
            computerMove();
        }
    }

    /**
     * Chọn kích thước cho người chơi.
     * Nếu người chơi là máy tính, không thực hiện lựa chọn.
     *
     * @param event Sự kiện nhấn nút chọn kích thước.
     */
    @Override
    void selectSize(ActionEvent event) {
        if (profile instanceof ComputerProfile) return;

        super.selectSize(event);
    }

    /**
     * Chọn kích thước cho đối thủ.
     * Nếu đối thủ là máy tính, không thực hiện lựa chọn.
     *
     * @param event Sự kiện nhấn nút chọn kích thước.
     */
    @Override
    void opponentSelectSize(ActionEvent event) {
        if (opponentProfile instanceof ComputerProfile) return;

        super.opponentSelectSize(event);
    }

    /**
     * Gửi yêu cầu từ bỏ trò chơi.
     * Nếu người chơi là máy tính, kết thúc trò chơi.
     */
    @Override
    void sendResign() {
        endGame(profile instanceof ComputerProfile ? profile.getUsername() : opponentProfile.getUsername(), profile.getScore());
    }

    /**
     * Kiểm tra xem lượt hiện tại có phải của máy tính hay không.
     *
     * @return true nếu lượt là của máy tính, false nếu không.
     */
    public boolean checkTurnComputer() {
        Profile profile = this.step % 2 == 1 ? this.profile : this.opponentProfile;
        return profile instanceof ComputerProfile;
    }

    /**
     * Thực hiện lượt đi của máy tính.
     * Tìm kiếm nước đi tốt nhất và thực hiện nó.
     */
    public void computerMove() {
        new Thread(() -> {
            Move move = com.findBestMove(game);
            if (move == null) {
                Platform.runLater(() -> {
                    endGame(game.getCurrentPlayer(), profile.getScore());
                });
                return;
            }

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                if (game.move(move)) {
                    move(move, 0);
                    step = move.getStep() + 1;

                    // Kiểm tra kết thúc game
                    String winner = game.getWinner();
                    if (winner != null) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            Platform.runLater(() -> {
                                endGame(winner, profile.getScore());
                            });
                        }).start();
                    }
                }

                if (this.checkTurnComputer()) {
                    computerMove();
                }
            });
        }).start();
    }
}
