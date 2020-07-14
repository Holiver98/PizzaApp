package Model;

import java.util.Objects;

public class Ingredient {
    private String name;
    private IngredientType type;
    private float price;

    public String getName() {
        return name;
    }

    public IngredientType getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
