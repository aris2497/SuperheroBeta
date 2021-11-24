package com.example.Superhero.dao;

import com.example.Superhero.dto.Organisation;
import com.example.Superhero.dto.SuperCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganisationDaoDb implements OrganisationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Organisation> getAllSuperCharacterOrganisations(SuperCharacter superChar) {
        return null;
    }

    @Override
    public List<Organisation> getAllOrganisation() {
        String SELECT_ALL_LOCATIONS = "SELECT * FROM organisation";
        return this.jdbc.query("SELECT * FROM organisation", new OrganisationDaoDb.OrganisationMapper());
    }
    public static final class OrganisationMapper implements RowMapper<Organisation> {
        public OrganisationMapper() {
        }

        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            Organisation org = new Organisation();
            org.setOrganisationId(rs.getInt("organisationId"));
            org.setName(rs.getString("name"));
            org.setAddress(rs.getString("description"));
            org.setDescription(rs.getString("address"));
            return org;
        }
    }




}
