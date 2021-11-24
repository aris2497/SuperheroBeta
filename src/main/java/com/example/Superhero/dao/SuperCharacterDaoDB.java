package com.example.Superhero.dao;

import com.example.Superhero.dto.Location;
import com.example.Superhero.dto.SuperCharacter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SuperCharacterDaoDB implements SuperCharacterDao {
    @Autowired
    JdbcTemplate jdbc;

    public SuperCharacterDaoDB() {
    }

    public List<SuperCharacter> getAllSuperCharactersByLocation(Location location) {
        String SELECT_SUPERCHAR_BY_LOCATION = "SELECT s.* FROM superCharacter s JOIN sighting sg ON sg.superCharacter = s.superId WHERE sg.location = ?";
        return this.jdbc.query("SELECT s.* FROM superCharacter s JOIN sighting sg ON sg.superCharacter = s.superId WHERE sg.location = ?",
                new SuperCharacterDaoDB.SuperCharacterMapper(), new Object[]{location.getId()});
    }

    @Override
    public List<SuperCharacter> getAllSuperCharactersByOrganisation(int id) {
        String SELECT_SUPERCHAR_BY_ORGANISATION = "SELECT s.*\n" +
                "FROM superCharacter s\n" +
                "JOIN heroOrganisation ho\n" +
                "  ON s.superId = ho.superID\n" +
                "JOIN organisation o\n" +
                "  ON o.organisationId = ho.organisationID\n" +
                "WHERE ho.organisationID = ?";
        return this.jdbc.query(SELECT_SUPERCHAR_BY_ORGANISATION,
                new SuperCharacterDaoDB.SuperCharacterMapper(), new Object[]{id});

    }

    public SuperCharacter getSuperCharacterById(int id) {
        try {
            String SELECT_SUPERCHAR_BY_ID = "SELECT * FROM superCharacter WHERE superId = ?";
            return (SuperCharacter)this.jdbc.queryForObject("SELECT * FROM superCharacter WHERE superId = ?",
                    new SuperCharacterDaoDB.SuperCharacterMapper(), new Object[]{id});
        } catch (DataAccessException var3) {
            return null;
        }
    }

    @Transactional
    public SuperCharacter addSuperCharacter(SuperCharacter superChar) {
        String INSERT_SUPERCHARACTER = "INSERT INTO superCharacter(name, description, superpower) VALUES(?,?,?)";
        this.jdbc.update(INSERT_SUPERCHARACTER,
                superChar.getName(),
                superChar.getDescription(),
                superChar.getSuperpower());
        int newId = (Integer)this.jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superChar.setId(newId);
        return superChar;
    }


    public void updateSuperCharacter(SuperCharacter superChar) {
        String UPDATE_STUDENT = "UPDATE superCharacter SET name = ?, description = ?, superpower = ? WHERE superId = ?";
        this.jdbc.update("UPDATE superCharacter SET name = ?, description = ?, superpower = ? WHERE superId = ?", new Object[]{superChar.getName(), superChar.getDescription(), superChar.getSuperpower(), superChar.getId()});
    }

    @Transactional
    public void deleteSuperCharacterById(int id) {
        String DELETE_HERO_ORGANISATION = "DELETE FROM heroOrganisation WHERE superId = ?";
        this.jdbc.update("DELETE FROM heroOrganisation WHERE superId = ?", new Object[]{id});
        String DELETE_STUDENT = "DELETE FROM superCharacter WHERE superId = ?";
        this.jdbc.update("DELETE FROM superCharacter WHERE superId = ?", new Object[]{id});
    }

    public List<SuperCharacter> getAllSuperCharacters() {
        String SELECT_ALL_SUPERCHARACTERS = "SELECT * FROM superCharacter";
        return this.jdbc.query("SELECT * FROM superCharacter", new SuperCharacterDaoDB.SuperCharacterMapper());
    }


    public static final class SuperCharacterMapper implements RowMapper<SuperCharacter> {
        public SuperCharacterMapper() {
        }

        public SuperCharacter mapRow(ResultSet rs, int index) throws SQLException {
            SuperCharacter superChar = new SuperCharacter();
            superChar.setId(rs.getInt("superId"));
            superChar.setName(rs.getString("name"));
            superChar.setDescription(rs.getString("description"));
            superChar.setSuperpower(rs.getString("superpower"));
            return superChar;
        }
    }
}
