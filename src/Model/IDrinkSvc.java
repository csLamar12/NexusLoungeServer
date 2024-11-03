package Model;

import java.util.List;

public interface IDrinkSvc {
    public void insertDrink(Drink drink);
    public void updateDrink(Drink drink);
    public void deleteDrink(int Id);
    public Drink getDrinkById(int Id);
    public List<Drink> getDrinkByType(boolean isAlcoholic);
    public List<Drink> getAllDrinks();
}
