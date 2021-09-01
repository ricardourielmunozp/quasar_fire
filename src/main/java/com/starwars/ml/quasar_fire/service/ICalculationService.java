package com.starwars.ml.quasar_fire.service;

import com.starwars.ml.quasar_fire.entity.ShipEntity;
import com.starwars.ml.quasar_fire.exception.DarkSideException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public interface ICalculationService {

    public void calculation(RequestEntity requestEntity) throws DarkSideException;

    public void calculationReciver(String name,RequestEntity requestEntity) throws DarkSideException;

    public ShipEntity getLocation() throws DarkSideException;
    
}
