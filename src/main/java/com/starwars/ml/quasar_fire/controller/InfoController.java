package com.starwars.ml.quasar_fire.controller;

import com.starwars.ml.quasar_fire.entity.SateliteEntity;
import com.starwars.ml.quasar_fire.exception.DarkSideException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.starwars.ml.quasar_fire.service.ICalculationService;

@RestController
@RequestMapping(path="/topsecret_split")
public class InfoController {

   @Autowired
   private ICalculationService calculation; 

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity interceptedCom(RequestEntity<SateliteEntity> requestEntity) throws DarkSideException {
        calculation.calculation(requestEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/{reciver}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity interceptedComReciver(RequestEntity requestEntity) throws DarkSideException {
        calculation.calculation(requestEntity);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity location(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
