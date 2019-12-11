package cn.edu.sjtu.bpmproject.server.exception;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String message) {
        super(message);
    }
}
