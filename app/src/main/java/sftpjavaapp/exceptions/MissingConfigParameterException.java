package sftpjavaapp.exceptions;

public class MissingConfigParameterException extends Exception {
    public MissingConfigParameterException(String parameter) {
        super(parameter + " parameter is missing or cannot be recognized");
    }
}
