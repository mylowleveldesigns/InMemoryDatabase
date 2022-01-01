package com.umang.lld.InMemoryDatabase.Services;

import com.umang.lld.InMemoryDatabase.Datastore.Table;
import com.umang.lld.InMemoryDatabase.Datastore.TableHeader;
import com.umang.lld.InMemoryDatabase.Datastore.TableRow;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DatabaseManagerService {
    private Map<String, Table> allTables; // Map of table name to actual Table;

    public DatabaseManagerService() {
        this.allTables = new HashMap<>();
    }

    public Table createTable(String name, List<TableHeader> tableHeaders) throws Exception {
        if(allTables.containsKey(name)){
            throw new Exception("A table with given name already present in database");
        }
        Table newTable = new Table(name, tableHeaders);
        allTables.put(name, newTable);
        return newTable;
    }

    public void deleteTable(String name) throws Exception {
        if(!allTables.containsKey(name)){
            throw new Exception("No table found with given name");
        }
        allTables.remove(name);
        return;
    }

    public int insertEntryinTable(String tableName , List<Object> values) throws Exception {
        if(!allTables.containsKey(tableName)){
            throw new Exception("No table found with given name");
        }
        Table tableToInsert = allTables.get(tableName);
        return tableToInsert.insertRowIntoTable(values);
    }

    public boolean deleteRowInTableFromRowId(String tableName, int rowId) throws Exception {
        if(!allTables.containsKey(tableName)){
            throw new Exception("No table found with given name");
        }
        Table tableToDeleteFrom = allTables.get(tableName);
        tableToDeleteFrom.deleteRowFromRowId(rowId);
        return false;
    }

    public List<String> getAllTableNames(){
        return allTables.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<TableRow> getAllTableRowOfTable(String tableName) throws Exception {
        if(!allTables.containsKey(tableName)){
            throw new Exception("No table found with given name");
        }
        return allTables.get(tableName).getRowsList();
    }
}
