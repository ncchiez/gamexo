package com.example.xogame.data.account;

/**
 * Lớp Profile đại diện cho thông tin hồ sơ của một người chơi trong trò chơi.
 */
public class Profile {
    private String username;
    private String name;
    private int score;
    private int matches;
    private int win;
    private int lose;
    private int draw;

    /**
     * Khởi tạo một đối tượng Profile với các thông tin người chơi cụ thể.
     *
     * @param username Tên đăng nhập của người chơi.
     * @param name Tên hiển thị của người chơi.
     * @param score Điểm số của người chơi.
     * @param matches Tổng số trận đấu đã tham gia.
     * @param win Số trận thắng.
     * @param lose Số trận thua.
     * @param draw Số trận hòa.
     */
    public Profile(String username, String name, int score, int matches, int win, int lose, int draw) {
        this.username = username;
        this.name = name;
        this.score = score;
        this.matches = matches;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    // Các phương thức getter và setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
