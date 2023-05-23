package com.koreait.schedulStat;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
@RequiredArgsConstructor
public class DailyCount {

    private final JdbcTemplate jdbcTemplate;

    public void total(long count) {
        String sql = "INSERT INTO DAILYBOARDCOUNT (BOARDCOUNT) VALUES (?)";

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, count);

            return pstmt;
        });
    }
}
