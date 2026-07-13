package com.example.EMS.Controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesstController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public String getCurrentDatabase() throws Exception {

        return "Connected Database : "
                + dataSource.getConnection().getCatalog();

    }
}