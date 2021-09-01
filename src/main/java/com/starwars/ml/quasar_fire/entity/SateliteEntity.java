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


    private List<Satellite> satellites = new ArrayList<Satellite>();

    private final String satDot = "satellites.";
    private final String dotPos = ".name";

    public List<Satellite> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<Satellite> satellites) {
        this.satellites = satellites;
    }

    public double[] getDistances(){

        double [] distances = new double[satellites.size()];
        for(int i = 0; i < satellites.size(); i ++){
            distances[i] = satellites.get(i).getDistance();
        }
        return  distances;
    }

    public double[][] getPositions(){
        double [][] positions = new double[satellites.size()][];
        String[] points;
        for(int i = 0; i < satellites.size(); i ++){
            if(satellites.get(i).getPosition() != null) {
                points = satellites.get(i).getPosition().toString().split(",");
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
            satellites.get(i).setPosition(position);
        }
    }

    public List<List<String>> getMessages(){
        List<List<String>> messages = new ArrayList<>();
        for(Satellite s : satellites){
            messages.add(s.getMessage());
        }
        return  messages;
    }

    public void updateReciver(Satellite rec){
        String searchName=rec.getName();
        OptionalInt indexOpt = IntStream.range(0, satellites.size())
                .filter(i -> searchName.equals(satellites.get(i).getName()))
                .findFirst();
        if(indexOpt.isPresent()) {
            satellites.set(indexOpt.getAsInt(), rec);
        }else{
            satellites.add(rec);
        }
    }
}
