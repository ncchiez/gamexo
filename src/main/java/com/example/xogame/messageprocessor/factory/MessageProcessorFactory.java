package com.example.xogame.messageprocessor.factory;

import com.example.xogame.messageprocessor.Default;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.messageprocessor.log.ForgotPass;
import com.example.xogame.messageprocessor.log.Login;
import com.example.xogame.messageprocessor.log.Signup;
import com.example.xogame.messageprocessor.match.EndGame;
import com.example.xogame.messageprocessor.match.FindMatch;
import com.example.xogame.messageprocessor.match.MoveProcessor;
import com.example.xogame.messageprocessor.room.CreateRoom;
import com.example.xogame.messageprocessor.room.ExitRoom;
import com.example.xogame.messageprocessor.room.JoinRoom;

/**
 * Lớp MessageProcessorFactory là một factory class dùng để tạo ra các đối tượng
 * MessageProcessor dựa trên mã hành động (action code) của thông điệp.
 */
public class MessageProcessorFactory {

    /**
     * Tạo một đối tượng MessageProcessor dựa trên mã hành động.
     *
     * @param actionCode Mã hành động được xác định trong thông điệp
     * @return Một đối tượng MessageProcessor tương ứng với mã hành động
     */
    public static MessageProcessor createDecryption(String actionCode) {
        return switch (actionCode) {
            case "login" -> new Login();
            case "signup" -> new Signup();
            case "forgot_pass" -> new ForgotPass();
            case "create_room" -> new CreateRoom();
            case "join_room" -> new JoinRoom();
            case "exit_room" -> new ExitRoom();
            case "find_match" -> new FindMatch();
            case "move" -> new MoveProcessor();
            case "end_game" -> new EndGame();
            default -> new Default();
        };
    }
}
