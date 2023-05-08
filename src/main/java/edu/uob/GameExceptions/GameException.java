package edu.uob.GameExceptions;

import java.io.Serial;

public class GameException extends Exception{
    @Serial
    private static final long serialVersionUID = 2322201277115415223L;
    String error = "[GAME Error]: ";
    String errorMessage;

    public GameException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return error + errorMessage;
    }
}
