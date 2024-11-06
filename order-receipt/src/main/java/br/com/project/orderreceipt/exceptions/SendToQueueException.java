package br.com.project.orderreceipt.exceptions;

public class SendToQueueException extends RuntimeException {
    public SendToQueueException(String message) {
        super(message);
    }
}
