package br.com.project.orderreceipt.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionOrder {
    private int code;
    private String message;
}
