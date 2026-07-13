package com.example.EMS.Config;

import java.util.List;

public class StoredProcedureConfig {

    private List<StoredProcedure> procedures;

    public StoredProcedureConfig() {
    }

    public List<StoredProcedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<StoredProcedure> procedures) {
        this.procedures = procedures;
    }

}