package com.example.xogame.data.match;

/**
 * Lớp Move đại diện cho một nước đi trong trò chơi, bao gồm thông tin về người thực hiện nước đi,
 * bước đi, vị trí trên bảng và kích thước của quân cờ.
 */
public class Move {
    private String username;
    private int step;
    private int row;
    private int col;
    private int size;

    /**
     * Khởi tạo một nước đi mới.
     *
     * @param username Tên của người thực hiện nước đi.
     * @param step Bước đi của nước đi.
     * @param row Vị trí hàng trên bảng cờ.
     * @param col Vị trí cột trên bảng cờ.
     * @param size Kích thước của quân cờ.
     */
    public Move(String username, int step, int row, int col, int size) {
        this.username = username;
        this.step = step;
        this.row = row;
        this.col = col;
        this.size = size;
    }

    /**
     * Lấy tên của người thực hiện nước đi.
     *
     * @return Tên của người thực hiện nước đi.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Thiết lập tên của người thực hiện nước đi.
     *
     * @param username Tên của người thực hiện nước đi.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Lấy bước đi của nước đi.
     *
     * @return Bước đi của nước đi.
     */
    public int getStep() {
        return step;
    }

    /**
     * Thiết lập bước đi của nước đi.
     *
     * @param step Bước đi của nước đi.
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Lấy vị trí hàng trên bảng cờ.
     *
     * @return Vị trí hàng.
     */
    public int getRow() {
        return row;
    }

    /**
     * Thiết lập vị trí hàng trên bảng cờ.
     *
     * @param row Vị trí hàng.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Lấy vị trí cột trên bảng cờ.
     *
     * @return Vị trí cột.
     */
    public int getCol() {
        return col;
    }

    /**
     * Thiết lập vị trí cột trên bảng cờ.
     *
     * @param col Vị trí cột.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Lấy kích thước của quân cờ.
     *
     * @return Kích thước của quân cờ.
     */
    public int getSize() {
        return size;
    }

    /**
     * Thiết lập kích thước của quân cờ.
     *
     * @param size Kích thước của quân cờ.
     */
    public void setSize(int size) {
        this.size = size;
    }
}
