package edu.bu.fitnessfriend.fitnessfriend.model;

/**
 * Created by Karan on 9/23/2017.
 */

public class demographic {
    private String _name;
    private int _weight;
    private int _age;
    private int _feet;
    private int _inch;
    private String _activitylvl;

    public demographic(){
        _name = "";
        _weight = 0;
        _age = 0;
        _feet = 0;
        _inch = 0;
        _activitylvl = "";
    }

    public demographic(String name, int weight, int age, int feet, int inch, String activitylvl){
        _name = name;
        _weight = weight;
        _age = age;
        _feet = feet;
        _inch = inch;
        _activitylvl = activitylvl;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getWeight() {
        return _weight;
    }

    public void setWeight(int weight) {
        this._weight = weight;
    }

    public int getAge() {
        return _age;
    }

    public void setAge(int age) {
        this._age = age;
    }

    public int getFeet() {
        return _feet;
    }

    public void setFeet(int feet) {
        this._feet = feet;
    }

    public int getInch() {
        return _inch;
    }

    public void setInch(int inch) {
        this._inch = inch;
    }

    public String getActivitylvl() {
        return _activitylvl;
    }

    public void setActivitylvl(String activitylvl) {
        this._activitylvl = activitylvl;
    }
}
