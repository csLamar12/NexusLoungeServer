//Author: Danielle Johns
package Model;

import java.io.Serializable;

/**
 * Represents a drink with an ID, name, and alcoholic status.
 */
public class Drink implements Serializable {
    private int drinkId, quantity;
    private String name;
    private boolean isAlcoholic;
    private String description;


    /**
     * Constructs a Drink object with the specified drinkId, name, and alcoholic status.
     *
     * @param drinkId the unique ID of the drink
     * @param name the name of the drink
     * @param isAlcoholic whether the drink is alcoholic
     */
    public Drink(int drinkId, String name, boolean isAlcoholic, String description, int quantity) {
        this.drinkId = drinkId;
        this.name = name;
        this.isAlcoholic = isAlcoholic;
        this.quantity = quantity;
        this.description = description;
    }

    // Getter and Setter for drinkId
    public int getId() {
        return drinkId;
    }

    public void setId(int id) {
        this.drinkId = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for isAlcoholic
    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the Drink object, showing the ID, name, and alcoholic status.
     */
    @Override
    public String toString() {
        return "Drink{" +
                "drinkId=" + drinkId +
                ", name='" + name + '\'' +
                ", isAlcoholic=" + isAlcoholic +
                ", description='" + description + '\'' +
                '}';
    }
}
