package com.starwars.ml.quasar_fire.service.impl;

import com.starwars.ml.quasar_fire.entity.CoordinatesEntity;
import com.starwars.ml.quasar_fire.entity.ReciverEntity;
import com.starwars.ml.quasar_fire.entity.SateliteEntity;
import com.starwars.ml.quasar_fire.entity.TransportEntity;
import com.starwars.ml.quasar_fire.exception.DarkSideException;
import com.starwars.ml.quasar_fire.service.ICalculationService;
import com.starwars.ml.quasar_fire.service.ILocationService;
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
    private Environment environment;

    private final String satDot = "satellites.";
    private final String dotPos = ".position";
    private TransportEntity transport;
    private SateliteEntity globalSatelites;

    @Value("${sateliteNames}")
    private String[] sateliteArray;

    @Override
    public void calculation(RequestEntity requestEntity) throws DarkSideException {

         globalSatelites = (SateliteEntity)requestEntity.getBody();
        validations(globalSatelites);
        savePositions(globalSatelites);
        double[] points  = locationService.location(globalSatelites.getPositions() ,globalSatelites.getDistances());
        CoordinatesEntity coordinates =  new CoordinatesEntity(points);
        transport = new TransportEntity(coordinates,"");
    }

    @Override
    public void calculationReciver(RequestEntity requestEntity) throws DarkSideException {

        ReciverEntity reciver = (ReciverEntity)requestEntity.getBody();
        globalSatelites.updateReciver(reciver);
        if(!(globalSatelites.getMessages().size() < 2)) {
            return;
        }

        savePositions(globalSatelites);
        double[] points  = locationService.location(globalSatelites.getPositions() ,globalSatelites.getDistances());
        CoordinatesEntity coordinates =  new CoordinatesEntity(points);
        transport = new TransportEntity(coordinates,"");
    }

    private void savePositions(SateliteEntity satelliteEntity){
        if(satelliteEntity.getPositions()[0] == null) {
            int numberSat = Integer.parseInt(environment.getProperty("satellites.numbers"));
            double[][] pointsList = new double[numberSat][];
            String[] satellitePos;
            for (int i = 0; i < satelliteEntity.getRecivers().size(); i++) {
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
