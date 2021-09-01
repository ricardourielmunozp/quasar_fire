package com.starwars.ml.quasar_fire.entity;

public class TransportEntity extends ShipEntity{

    private String message;

    public TransportEntity(CoordinatesEntity coordinates, String msj){
        this.setPosition(coordinates);
        this.message = msj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
