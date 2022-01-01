package com.umang.lld.InMemoryDatabase.Datastore;

import java.sql.Timestamp;
import java.util.Map;

public class TableRow {
    private static int rowId;
    private Map<String, Object> columnValues;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public TableRow(int rowId, Map<String, Object> columnValues) {
        this.rowId = rowId;
        this.columnValues = columnValues;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public Map<String, Object> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(Map<String, Object> columnValues) {
        this.columnValues = columnValues;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
