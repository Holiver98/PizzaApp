package com.github.holiver98.model;

public enum PizzaSize {
    SMALL,
    NORMAL,
    LARGE,
    EXTRA_LARGE;

    @Override
    public String toString() {
        if(this.equals(PizzaSize.SMALL)){
            return "Small";
        }else if(this.equals(PizzaSize.NORMAL)){
            return "Normal";
        }else if(this.equals(PizzaSize.LARGE)){
            return "Large";
        }else if(this.equals(PizzaSize.EXTRA_LARGE)){
            return "Extra large";
        }else{
            return "";
        }
    }
}
