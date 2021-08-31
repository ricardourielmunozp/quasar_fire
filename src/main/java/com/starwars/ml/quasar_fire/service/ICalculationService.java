package com.starwars.ml.quasar_fire.service;

import com.starwars.ml.quasar_fire.exception.DarkSideException;
import org.springframework.http.RequestEntity;

public interface ICalculationService {

    public void calculation(RequestEntity requestEntity) throws DarkSideException;

    public void calculationReciver(RequestEntity requestEntity) throws DarkSideException;
    
}
