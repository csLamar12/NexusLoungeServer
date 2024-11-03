package Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestsSQLProvider extends SQLProvider implements IGuestsSvc{
    private static final Logger LOGGER = LogManager.getLogger(GuestsSQLProvider.class);
    private String query;

    public GuestsSQLProvider() {
        super();
    }

    @Override
    public void insertGuests(Guests g) {
        query = "INSERT INTO Guests (GuestID, DOB) VALUES (?,?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, g.getId());
            ps.setDate(2, new Date(g.getdOB().getTime()));
            ps.executeUpdate();
            LOGGER.info("Inserted Guest into database");
        } catch (SQLException e) {
            LOGGER.error("Failed to insert Guest into database");
        }
    }

    @Override
    public void updateGuests(Guests g) {
        query = "UPDATE Guests SET DOB = ? WHERE GuestID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setDate(1, new Date(g.getdOB().getTime()));
            ps.setInt(2, g.getId());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                LOGGER.info("Updated Guest with ID {}", g.getId());
            } else {
                LOGGER.warn("No Guest found with ID {}", g.getId());
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to update Guest with ID {}", g.getId(), e);
        }
    }

    @Override
    public void deleteGuests(Guests g) {
        query = "DELETE FROM Guests WHERE GuestID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, g.getId());
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                LOGGER.info("Deleted Guest with ID {}", g.getId());
            } else {
                LOGGER.warn("No Guest found with ID {}", g.getId());
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to delete Guest with ID {}", g.getId(), e);
        }
    }

    @Override
    public Guests searchGuests(Guests g) {
        query = "SELECT * FROM Guests WHERE GuestID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, g.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                Guests foundGuest = new Guests();
                foundGuest.setId(rs.getInt("GuestID"));
                foundGuest.setdOB(rs.getDate("DOB"));
                LOGGER.info("Found Guest with ID {}", g.getId());
                return foundGuest;
            } else {
                LOGGER.warn("No Guest found with ID {}", g.getId());
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to search Guest with ID {}", g.getId(), e);
        }
        return null;
    }

    @Override
    public List<Guests> getGuests() {
        query = "SELECT * FROM Guests";
        List<Guests> guestsList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Guests guest = new Guests();
                guest.setId(rs.getInt("GuestID"));
                guest.setdOB(rs.getDate("DOB"));
                guestsList.add(guest);
            }
            LOGGER.info("Fetched all Guests from database");
        } catch (SQLException e) {
            LOGGER.error("Failed to fetch Guests from database", e);
        }
        return guestsList;
    }
}
