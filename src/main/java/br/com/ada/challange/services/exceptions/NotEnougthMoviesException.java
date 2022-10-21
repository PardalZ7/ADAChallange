package br.com.ada.challange.services.exceptions;

public class NotEnougthMoviesException extends RuntimeException {

    public NotEnougthMoviesException(String message) {
        super(message);
    }

    public NotEnougthMoviesException(String message, Throwable cause) {
        super(message, cause);
    }

}
