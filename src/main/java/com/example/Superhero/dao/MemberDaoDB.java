package com.example.Superhero.dao;

import com.example.Superhero.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDaoDB implements MemberDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Member> getAllMembersByOrganisation(int organisationId) {
        try {
            String SELECT_MEMBERS_BY_ORG = "SELECT * FROM member WHERE organisation = ?";
            return this.jdbc.query(SELECT_MEMBERS_BY_ORG,
                    new MemberDaoDB.MemberMapper(), new Object[]{organisationId});
        } catch (DataAccessException var3) {
            return null;
        }
    }
    public static final class MemberMapper implements RowMapper<Member> {
        public MemberMapper() {
        }

        public Member mapRow(ResultSet rs, int index) throws SQLException {
            Member member = new Member();
            member.setId(rs.getInt("memberId"));
            member.setName(rs.getString("name"));
            member.setOrganisation(rs.getInt("organisation"));
            return member;
        }
    }
}
