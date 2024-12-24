package com.example.xogame.data.match;

/**
 * Lớp Pocket đại diện cho kho quân cờ của người chơi, bao gồm các quân nhỏ, vừa và lớn.
 */
public class Pocket {
    private int small;
    private int medium;
    private int large;
    private final int initialSize;

    /**
     * Khởi tạo một Pocket với số lượng quân cờ mặc định.
     */
    public Pocket() {
        this.small = 4;
        this.medium = 2;
        this.large = 1;
        this.initialSize = this.small + this.medium + this.large;
    }

    /**
     * Khởi tạo một Pocket với số lượng quân cờ cụ thể.
     *
     * @param small Số lượng quân cờ nhỏ.
     * @param medium Số lượng quân cờ vừa.
     * @param large Số lượng quân cờ lớn.
     */
    public Pocket(int small, int medium, int large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.initialSize = this.small + this.medium + this.large;
    }

    /**
     * Lấy số lượng quân cờ theo kích thước.
     *
     * @param size Kích thước quân cờ (0: nhỏ, 1: vừa, 2: lớn).
     * @return Số lượng quân cờ của kích thước tương ứng.
     */
    public int getNumberOfSize(int size) {
        if (size == 0) return this.small;
        if (size == 1) return this.medium;
        return large;
    }

    /**
     * Sử dụng một quân cờ theo kích thước.
     *
     * @param size Kích thước quân cờ (0: nhỏ, 1: vừa, 2: lớn).
     * @return true nếu quân cờ đã được sử dụng thành công, false nếu không còn quân cờ.
     */
    public boolean useSize(int size) {
        if (getNumberOfSize(size) == 0) return false;

        if (size == 0) small--;
        if (size == 1) medium--;
        if (size == 2) large--;
        return true;
    }

    /**
     * Tăng số lượng quân cờ theo kích thước.
     *
     * @param size Kích thước quân cờ (0: nhỏ, 1: vừa, 2: lớn).
     */
    public void increase(int size) {
        if (size == 0) small++;
        if (size == 1) medium++;
        if (size == 2) large++;
    }

    /**
     * Lấy số lượng quân cờ ban đầu.
     *
     * @return Số lượng quân cờ ban đầu.
     */
    public int initSize() {
        return this.initialSize;
    }

    /**
     * Lấy tổng số lượng quân cờ hiện có.
     *
     * @return Tổng số lượng quân cờ.
     */
    public int size() {
        return this.small + this.medium + this.large;
    }

    /**
     * Kiểm tra xem kho quân cờ có trống hay không.
     *
     * @return true nếu kho quân cờ trống, false nếu còn quân cờ.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }
}
