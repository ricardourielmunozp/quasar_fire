package com.starwars.ml.quasar_fire.controller;

import com.starwars.ml.quasar_fire.entity.SateliteEntity;
import com.starwars.ml.quasar_fire.entity.Satellite;
import com.starwars.ml.quasar_fire.exception.DarkSideException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.starwars.ml.quasar_fire.service.ICalculationService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path="/topsecrpsecret_split")
public class InfoController {

   @Autowired
   private ICalculationService calculation; 

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity interceptedCom(RequestEntity<SateliteEntity> requestEntity) throws DarkSideException {
        try {
            calculation.calculation(requestEntity);
            return new ResponseEntity(HttpStatus.OK);
        }catch (DarkSideException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping(value = "/{reciver}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity interceptedComReciver(@PathVariable String reciver, RequestEntity<Satellite> requestEntity) throws DarkSideException {
        try {
            calculation.calculationReciver(reciver,requestEntity);
            return new ResponseEntity(HttpStatus.OK);
        }catch (DarkSideException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

    }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity location() throws DarkSideException{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(calculation.getLocation());
        }catch (DarkSideException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
