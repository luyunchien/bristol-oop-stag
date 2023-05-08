package edu.uob.GameExceptions;

import java.io.Serial;

public class GameParseException extends GameException {
    @Serial
    private static final long serialVersionUID = -4333453132834466112L;

    public GameParseException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString(){
        return error + "error when parsing, " + errorMessage;
    }
}
