package com.umang.lld.InMemoryDatabase.Datastore;

import java.sql.Timestamp;
import java.util.*;

public class Table {
    private static int nextRowId;
    private String name;
    private static List<TableHeader> headerList;
    private List<TableRow> rowsList;
    private Timestamp createdAt;

    public Table(String name, List<TableHeader> headerList) {
        this.name = name;
        this.headerList = headerList;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.rowsList = new ArrayList<>();
        nextRowId = 0;
    }

    public int insertRowIntoTable(List<Object> values) throws Exception {
        validateRow(values);
        nextRowId++;
        Map<String, Object> valuesMap = new HashMap<>();
        for(int i =0;i<values.size();i++){
            valuesMap.put(headerList.get(i).name, values.get(i));
        }
        TableRow newRow = new TableRow(nextRowId, valuesMap);
        rowsList.add(newRow);
        return nextRowId;
    }

    public boolean deleteRowFromRowId(int rowId){
        Optional<TableRow> rowToDeleteOptional = rowsList.stream().filter(row -> row.getRowId() == rowId).findAny();

        if(!rowToDeleteOptional.isPresent()) {
            // No row with given rowId found, therefore return false
            return false;
        }

        rowsList.remove(rowToDeleteOptional.get());
        return true;
    }

    private static void validateRow(List<Object> values) throws Exception {
        if(values.size() != headerList.size()){
            throw new Exception("Number of items in row doesn't match number of headers in the table");
        }
        for(int i=0;i<values.size();i++){
            TableHeader currentHeader = headerList.get(i);
            Object currentValue = values.get(i);

            switch (currentHeader.constraint) {
                case STRING_LENGTH_20:
                    String stringValue = String.valueOf(currentValue);
                    if(stringValue.length() > 20){
                        throw new Exception("String Value at column: " + i + " violates constraint " + currentHeader.constraint.name() + " for value " + currentValue);
                    }
                    break;
                case INT_RANGE_1024:
                    int intValue = (int) currentValue;
                    if(intValue > 1024 || intValue < -1024){
                        throw new Exception("Integer value at columnt: " + i + " violates constraint " + currentHeader.constraint.name() + " for value " + currentValue);
                    }
                    break;
                case NONE:
                    break;
                default:
                    throw new Exception("Unknown constraint at column : " + i + " ");
            }
        }
    }

    public static int getNextRowId() {
        return nextRowId;
    }

    public static void setNextRowId(int nextRowId) {
        Table.nextRowId = nextRowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<TableHeader> getHeaderList() {
        return headerList;
    }

    public static void setHeaderList(List<TableHeader> headerList) {
        Table.headerList = headerList;
    }

    public List<TableRow> getRowsList() {
        return rowsList;
    }

    public void setRowsList(List<TableRow> rowsList) {
        this.rowsList = rowsList;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
