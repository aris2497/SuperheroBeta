package com.example.Superhero.dao;

import com.example.Superhero.dto.Location;
import com.example.Superhero.dto.SuperCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoDB implements LocationDao{

    @Autowired
    private JdbcOperations jdbc;

    public Location getLocationById(int id) {
        try {
            String SELECT_SUPERCHAR_BY_ID = "SELECT * FROM location WHERE locationId = ?";
            return (Location) this.jdbc.queryForObject(SELECT_SUPERCHAR_BY_ID,
                    new LocationDaoDB.LocationMapper(), new Object[]{id});
        } catch (DataAccessException var3) {
            return null;
        }
    }

    @Override
    public List<Location> getAllSuperCharLocation(int id) {
        return null;
    }

    @Override
    public List<Location> getAllLocations() {
        String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return this.jdbc.query("SELECT * FROM location", new LocationDaoDB.LocationMapper());
    }

    public static final class LocationMapper implements RowMapper<Location> {
        public LocationMapper() {
        }

        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("locationId"));
            loc.setName(rs.getString("name"));
            loc.setDescription(rs.getString("description"));
            loc.setAddress(rs.getString("address"));
            loc.setCoordinates(rs.getString("coordinates"));
            return loc;
        }
    }

}
