package com.starwars.ml.quasar_fire.entity;

public class TransportEntity extends ShipEntity{

    private String msj;

    public TransportEntity(CoordinatesEntity coordinates, String msj){
        this.setCoordinates(coordinates);
        this.msj = msj;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }
}
