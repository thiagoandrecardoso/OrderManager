package br.com.project.orderreceipt.model;

import lombok.Data;

@Data
public class ProtocolPublishedOrder {
    private String protocol;
    public ProtocolPublishedOrder(String protocol) {
        this.protocol = protocol;
    }
}
