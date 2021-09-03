package com.starwars.ml.quasar_fire;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.starwars.ml.quasar_fire.service.impl.LocationService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void getLocationWith3PositionsSmall(){
        double[][] positions = new double[][]{{20.0}, {10.0}, {25.0}};
        double[] distances = new double[]{1.1, 0.1, 0.9};
        double[] expectedPosition = new double[]{2.1};
        double[] calculatedPosition = locationService.location(positions, distances);

        for (int i = 0; i < calculatedPosition.length; i++) {
            System.out.println("****=="+calculatedPosition[i]);
            assertEquals(expectedPosition[i], calculatedPosition[i]);
        }
    }

    @Test
    public void getLocationWith3PositionsMedium() throws Exception {
        double[][] positions = new double[][]{{-500.0, -200.0}, {100.0, -100.0}, {500.0, 100.0}};
        double[] distances = new double[]{100.0, 115.5, 142.7};
        double[] expectedPosition = new double[]{-58.315252587138595, -69.55141837312165};
        double[] calculatedPosition = locationService.location(positions, distances);
        for (int i = 0; i < calculatedPosition.length; i++) {
            assertEquals(expectedPosition[i], calculatedPosition[i]);
        }
    }

    @Test
    public void getLocationWith4Positions(){
        double[][] positions = new double[][]{{5.0, -6.0}, {13.0, -15.0}, {21.0, -3.0}, {12.42, -21.2}};
        double[] distances = new double[]{8.06, 13.97, 23.32, 15.31};
        double[] expectedPosition = new double[]{-0.6, -11.8};
        double acceptedDelta = 1.0;
        double[] calculatedPosition = locationService.location(positions, distances);
        for (int i = 0; i < calculatedPosition.length; i++) {
            assertEquals(expectedPosition[i], calculatedPosition[i], acceptedDelta);
        }
    }
}
