package com.starwars.ml.quasar_fire.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class SateliteEntity {

    @Value("${sateliteNames}")
    private String[] sateliteArray;

    @Autowired
    private Environment environment;

    private List<ReciverEntity> recivers;

    private final String satDot = "satellites.";
    private final String dotPos = ".name";

    public List<ReciverEntity> getRecivers() {
        return recivers;
    }

    public void setRecivers(List<ReciverEntity> recivers) {
        this.recivers = recivers;
    }

    public double[] getDistances(){

        double [] distances = new double[recivers.size()];
        for(int i = 0; i < recivers.size(); i ++){
            distances[i] = recivers.get(i).getDistance();
        }
        return  distances;
    }

    public double[][] getPositions(){
        double [][] positions = new double[recivers.size()][];
        String[] points;
        for(int i = 0; i < recivers.size(); i ++){
            if(recivers.get(i).getCoordinates() != null) {
                points = recivers.get(i).getCoordinates().toString().split(",");
                positions[i] = Arrays.stream(points)
                        .map(Double::valueOf)
                        .mapToDouble(Double::doubleValue)
                        .toArray();
            }
        }
        return positions;
    }

    public void setPositions(double[][] pointsList){
        CoordinatesEntity position;
        for(int i = 0; i < pointsList.length; i++){
            position = new CoordinatesEntity(pointsList[i]);
            recivers.get(i).setCoordinates(position);
        }
    }

    public List<List<String>> getMessages(){
        List<List<String>> messages = new ArrayList<>();
        for(ReciverEntity s : recivers){
            messages.add(s.getMessage());
        }
        return  messages;
    }

    public void updateReciver(ReciverEntity rec){
        String searchName=rec.getName();
        OptionalInt indexOpt = IntStream.range(0, recivers.size())
                .filter(i -> searchName.equals(recivers.get(i)))
                .findFirst();
        recivers.set(indexOpt.getAsInt(),rec);
    }
}
