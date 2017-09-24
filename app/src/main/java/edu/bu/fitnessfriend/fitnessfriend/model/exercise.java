package edu.bu.fitnessfriend.fitnessfriend.model;

/**
 * Created by karan on 9/23/2017.
 */

public class exercise {

    private String _exerciseName;
    private double _calorie;
    private double _duration;
    private String _unit;

    public exercise(){
        _exerciseName = "";
        _calorie = 0;
        _duration = 0;
        _unit = "";
    }

    public exercise(String exerciseName, double calorie, double duration, String unit) {
        _exerciseName = exerciseName;
        _calorie = calorie;
        _duration = duration;
        _unit = unit;
    }

    public void setExerciseName(String exerciseName) {
        _exerciseName = exerciseName;
    }

    public String getExerciseName(){
        return _exerciseName;
    }

    public void setCalorie(double calorie){
        _calorie = calorie;
    }

    public double getCalorie(){
        return _calorie;
    }

    public void setDuration(double duration){
        _duration = duration;
    }

    public double getDuration(){
        return _duration;
    }

    public void setUnit(String unit){
        _unit = unit;
    }

    public String getUnit(){
        return _unit;
    }

}
