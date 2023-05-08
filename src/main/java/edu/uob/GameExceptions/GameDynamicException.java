package edu.uob.GameExceptions;

import java.io.Serial;

public class GameDynamicException extends GameException{
    @Serial
    private static final long serialVersionUID = 704305685462926727L;

    public GameDynamicException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return error + "cannot execute dynamic action, " + errorMessage;
    }
}
