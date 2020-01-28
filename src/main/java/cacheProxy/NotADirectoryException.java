package cacheProxy;

public class NotADirectoryException extends RuntimeException {

    public NotADirectoryException() {
        super("Указанный путь не является директорией");
    }

}
