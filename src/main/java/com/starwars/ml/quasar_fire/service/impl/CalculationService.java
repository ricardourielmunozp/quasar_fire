package com.starwars.ml.quasar_fire.service.impl;

import com.starwars.ml.quasar_fire.entity.*;
import com.starwars.ml.quasar_fire.exception.DarkSideException;
import com.starwars.ml.quasar_fire.service.ICalculationService;
import com.starwars.ml.quasar_fire.service.ILocationService;
import com.starwars.ml.quasar_fire.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import java.util.Arrays;

@Service
public class CalculationService implements ICalculationService {

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private Environment environment;

    private final String satDot = "satellites.";
    private final String dotPos = ".position";
    private TransportEntity transport;
    private SateliteEntity globalSatelites = new SateliteEntity();

    @Value("${sateliteNames}")
    private String[] sateliteArray;


    @Override
    public void calculation(RequestEntity requestEntity) throws DarkSideException {
         globalSatelites = (SateliteEntity)requestEntity.getBody();
    }

    @Override
    public void calculationReciver(String name,RequestEntity requestEntity) throws DarkSideException {
        Satellite reciver = (Satellite)requestEntity.getBody();
        reciver.setName(name);
        globalSatelites.updateReciver(reciver);
    }

    @Override
    public ShipEntity getLocation() throws DarkSideException{

        if(globalSatelites == null || globalSatelites.getMessages().size() < 2)
            throw new DarkSideException(environment.getProperty("error.msj.incomplete"));

        savePositions(globalSatelites);
        if( (globalSatelites.getPositions().length < 2) || (globalSatelites.getDistances().length < 2) )
            throw new DarkSideException(environment.getProperty("error.sat.incomplete")   );


        double[] points  = locationService.location(globalSatelites.getPositions() ,globalSatelites.getDistances());
        CoordinatesEntity coordinates =  new CoordinatesEntity(points);

        return new TransportEntity(coordinates, messageService.getMessage(globalSatelites.getMessages()));
    }

    private void savePositions(SateliteEntity satelliteEntity){
        if(satelliteEntity.getPositions()[0] == null) {
            int numberSat = Integer.parseInt(environment.getProperty("satellites.numbers"));
            double[][] pointsList = new double[numberSat][];
            String[] satellitePos;
            for (int i = 0; i < satelliteEntity.getSatellites().size(); i++) {
                satellitePos = environment.getProperty(satDot + i + dotPos).split(",");
                pointsList[i] = Arrays.stream(satellitePos)
                        .map(Double::valueOf)
                        .mapToDouble(Double::doubleValue)
                        .toArray();
            }
            satelliteEntity.setPositions(pointsList);
        }
    }

    private void validations(SateliteEntity satellite) throws DarkSideException {

        if(satellite.getMessages().size() < 2)
            throw new DarkSideException(environment.getProperty("error.msj.incomplete"));

        if( (satellite.getPositions().length < 2) || (satellite.getDistances().length < 2) )
            throw new DarkSideException(environment.getProperty("error.sat.incomplete")   );

    }
    
}
