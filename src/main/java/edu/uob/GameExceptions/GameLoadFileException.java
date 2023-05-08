package edu.uob.GameExceptions;

import java.io.Serial;

public class GameLoadFileException extends GameException{
    @Serial
    private static final long serialVersionUID = 5987343763215437926L;

    public GameLoadFileException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return error + "error when loading the files, " + errorMessage;
    }

}
