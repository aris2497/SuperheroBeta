package com.example.Superhero.dao;

import com.example.Superhero.dto.Location;
import com.example.Superhero.dto.Sighting;
import com.example.Superhero.dto.SuperCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Sighting> getAllSightingByDate() {
        String SELECT_ALL_SUPERCHARACTERS = "SELECT * FROM sighting";
        return this.jdbc.query(SELECT_ALL_SUPERCHARACTERS, new SightingDaoDB.SightingMapper());
    }

    @Override
    public Sighting getSightingByLocationAndTime(Location location, String time) {
        return null;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {
        public SightingMapper() {
        }

        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("sightingId"));
            sighting.setLocation(rs.getString("location"));
            sighting.setSuperCharacter(rs.getString("superCharacter"));
            sighting.setDateOf(rs.getString("dateOf"));
            return sighting;
        }
    }
}
