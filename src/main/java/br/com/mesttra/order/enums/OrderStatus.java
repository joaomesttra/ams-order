package br.com.mesttra.order.enums;

public enum OrderStatus {

    PREPARING("PREPARING"), DONE("DONE");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
