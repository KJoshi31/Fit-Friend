package edu.bu.fitnessfriend.fitnessfriend.model;

/**
 * Created by karan on 9/23/2017.
 */

public class food {

    private String _foodName;
    private double _quantity;
    private double _calorie;

    public food(){
        this._foodName = "";
        this._quantity = 0;
        this._calorie = 0;
    }

    public food(String foodName, double quantity, double calorie){
        _foodName = foodName;
        _quantity = quantity;
        _calorie = calorie;
    }

    public void setFoodName(String foodName){
        _foodName = foodName;
    }

    public String getFoodName(){
        return _foodName;
    }

    public void setQuantity(double quantity){
        _quantity = quantity;
    }

    public double getQuantity(){
        return _quantity;
    }

    public void setCalorie(double calorie){
        _calorie = calorie;
    }

    public double getCalorie() {
        return _calorie;
    }

}
