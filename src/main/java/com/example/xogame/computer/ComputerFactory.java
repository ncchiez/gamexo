package com.example.xogame.computer;

/**
 * Factory class để tạo các đối tượng máy tính dựa trên mức độ khó.
 */
public class ComputerFactory {
    /**
     * Tạo một đối tượng máy tính dựa trên mức độ được chỉ định.
     *
     * @param level Mức độ khó của máy tính:
     *              0 - Dễ,
     *              1 - Trung bình,
     *              2 - Khó.
     * @return Đối tượng máy tính tương ứng với mức độ đã chỉ định.
     * @throws IllegalStateException Nếu mức độ không hợp lệ.
     */
    public static Computer createComputer(int level) {
        return switch (level) {
            case 0 -> new EasyComputer();
            case 1 -> new MediumComputer();
            case 2 -> new HardComputer();
            default -> throw new IllegalStateException("Unexpected value: " + level);
        };
    }
}
