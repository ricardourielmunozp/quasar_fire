package com.starwars.ml.quasar_fire.service.impl;

import com.starwars.ml.quasar_fire.service.ILocationService;
import com.starwars.ml.quasar_fire.trilateration.NonLinearLeastSquaresSolver;
import com.starwars.ml.quasar_fire.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {

    @Override
    public double[] location(double[][] positions, double [] distances){
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
        NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new LevenbergMarquardtOptimizer());

        return  nSolver.solve().getPoint().toArray();
    }

}
