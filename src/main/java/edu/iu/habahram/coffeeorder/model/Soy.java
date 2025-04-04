package edu.iu.habahram.coffeeorder.model;

public class Soy extends Beverage{

    @Override
    public String getDescription() {
        return "Soy";
    }
    @Override
    public float cost() {
        return 0.27F;
    }

}
