package com.example.EMS.Config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StoredProcedureExecutor {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StoredProcedureLoader loader;

    /**
     * Execute Stored Procedure returning List
     */
    public <T> List<T> query(String key,
                             Class<T> clazz,
                             Object... params) {

        StoredProcedure sp = loader.getProcedure(key);

        String sql = buildSql(sp.getProcedure(), params.length);

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(clazz),
                params);
    }

    /**
     * Execute Stored Procedure returning affected rows
     */
    public int update(String key,
                      Object... params) {

        StoredProcedure sp = loader.getProcedure(key);

        String sql = buildSql(sp.getProcedure(), params.length);

        return jdbcTemplate.update(
                sql,
                params);
    }

    /**
     * Builds
     * EXEC sp_Name ?,?,?
     */
    private String buildSql(String procedure,
                            int paramCount) {

        StringBuilder sql =
                new StringBuilder("EXEC ");

        sql.append(procedure);

        if (paramCount > 0) {

            sql.append(" ");

            for (int i = 0; i < paramCount; i++) {

                sql.append("?");

                if (i < paramCount - 1) {
                    sql.append(", ");
                }

            }
        }

        return sql.toString();
    }

}
