package edu.uob.GameExceptions;

import java.io.Serial;

public class GameExecuteException extends GameException{

    @Serial
    private static final long serialVersionUID = 5519511588338799128L;

    public GameExecuteException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return error + "error when executing, " + errorMessage;
    }
}
