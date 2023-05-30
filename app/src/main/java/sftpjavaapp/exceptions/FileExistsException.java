package sftpjavaapp.exceptions;

public class FileExistsException extends Exception {
    public FileExistsException(String parameter) {
        super("File or directory already exists: " + parameter);
    }
}
