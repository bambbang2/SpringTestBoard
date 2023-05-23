package com.koreait.models.board;

import com.koreait.controllers.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class BoardDao {

    private final JdbcTemplate jdbcTemplate;

    public void insert(BoardForm boardForm){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO BOARDDATA (ID, SUBJECT, CONTENT) VALUES (BOARDDATA_SEQ.nextval, ? , ?)";
        jdbcTemplate.update(conn -> {
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"ID"});

            pstmt.setString(1, boardForm.getSubject());
            pstmt.setString(2, boardForm.getContent());

            return pstmt;
        }, keyHolder);
        Number key = keyHolder.getKey();
        boardForm.setId(key.longValue());
    }


    public void update(BoardForm boardForm){
        String sql = "UPDATE BOARDDATA SET SUBJECT = ?, CONTENT = ? WHERE ID = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, boardForm.getSubject());
            pstmt.setString(2, boardForm.getContent());
            pstmt.setLong(3, boardForm.getId());

            return pstmt;
        });
    }


    public void delete(Long id){
        String sql = "DELETE FROM BOARDDATA WHERE ID = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);

            return pstmt;
        });
    }

    public boolean exists(Long id) {
        String sql = "SELECT COUNT(*) FROM BOARDDATA WHERE ID = ?";

        long cnt = jdbcTemplate.queryForObject(sql, Long.class, id);

        return cnt > 0;
    }

    public Board get(long id){
        try {
            String sql = "SELECT * FROM BOARDDATA WHERE ID = ?";
            Board board = jdbcTemplate.queryForObject(sql, this::mapper, id);
            return board;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Board> gets(){
        String sql = "SELECT * FROM BOARDDATA ORDER BY REGDT DESC";
        List<Board> items = jdbcTemplate.query(sql, this::mapper);

        return items;
    }


    public long getTotal(){
        String sql = "SELECT COUNT(*) FROM BOARDDATA board WHERE TRUNC(REGDT) = TRUNC(SYSDATE-1)";
        long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count;
    }


    private Board mapper(ResultSet rs, int i) throws SQLException {
        Board board = new Board();
        board.setId(rs.getLong("ID"));
        board.setSubject(rs.getString("SUBJECT"));
        board.setContent(rs.getString("CONTENT"));
        board.setRegDt(rs.getTimestamp("REGDT").toLocalDateTime());

        return board;
    }
}