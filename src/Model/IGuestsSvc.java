package Model;

import java.util.List;

public interface IGuestsSvc {
    public void insertGuests(Guests g);
    public void updateGuests(Guests g);
    public void deleteGuests(Guests g);
    public Guests searchGuests(Guests g);
    public List<Guests> getGuests();
}
