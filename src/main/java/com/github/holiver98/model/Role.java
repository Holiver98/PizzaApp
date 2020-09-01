package com.github.holiver98.model;

public enum Role {
    CUSTOMER,
    CHEF;

    @Override
    public String toString() {
        if(this.equals(Role.CHEF)){
            return "Chef";
        }else if(this.equals(Role.CUSTOMER)){
            return "Customer";
        }else{
            return "";
        }
    }
}
