package com.umang.lld.InMemoryDatabase;

import com.umang.lld.InMemoryDatabase.Datastore.TableHeader;
import com.umang.lld.InMemoryDatabase.Datastore.TableRow;
import com.umang.lld.InMemoryDatabase.Services.DatabaseManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InMemoryDatabaseApplicationTests {

    @Autowired
    private DatabaseManagerService dbService;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllTablesTest() {
        try {
            dbService.createTable("Employee", Arrays.asList(
                    new TableHeader("Name", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20),
                    new TableHeader("Age", TableHeader.TYPES.INT, false, TableHeader.Constraint.INT_RANGE_1024)
            ));

            dbService.createTable("payment_transaction", Arrays.asList(
                    new TableHeader("paymentId", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20),
                    new TableHeader("paymendDetails", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20)
            ));
            List<String> allTableNamesResponse = dbService.getAllTableNames();
            List<String> expectedResponse = Arrays.asList("Employee", "payment_transaction");
            assertEquals(allTableNamesResponse, expectedResponse);
        } catch (Exception e) {
            System.out.println("Exception in createTableTest " + e.getMessage());
        }
    }

    @Test
    void getTableDataTest() {
        try {
            dbService.createTable("Employee", Arrays.asList(
                    new TableHeader("Name", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20),
                    new TableHeader("Age", TableHeader.TYPES.INT, false, TableHeader.Constraint.INT_RANGE_1024)
            ));

            dbService.insertEntryinTable("Employee", Arrays.asList("Umang", 26));
            dbService.insertEntryinTable("Employee", Arrays.asList("Test", 30));

            List<String> observedEntriesName = dbService.getAllTableRowOfTable("Employee").stream().map(TableRow::getColumnValues)
                    .map(hm -> hm.get("Name"))
                    .map(o -> (String)o).collect(Collectors.toList());
            List<String> expected = Arrays.asList("Umang", "Test");
            System.out.println(observedEntriesName);
            assertEquals(observedEntriesName, expected);
        } catch (Exception e) {
            System.out.println("Exception in getTableDataTest" + e.getMessage());
        }
    }

    @Test
    void insertRowConstraintTest(){
        try{
            dbService.createTable("Employee", Arrays.asList(
                    new TableHeader("Name", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20),
                    new TableHeader("Age", TableHeader.TYPES.INT, false, TableHeader.Constraint.INT_RANGE_1024)
            ));

            dbService.insertEntryinTable("Employee", Arrays.asList("abcdeghijklmnopqrstuvwxyzab", 26));
            dbService.insertEntryinTable("Employee", Arrays.asList("Test", 30));

            List<String> observedEntriesName = dbService.getAllTableRowOfTable("Employee").stream().map(TableRow::getColumnValues)
                    .map(hm -> hm.get("Name"))
                    .map(o -> (String)o).collect(Collectors.toList());
            List<String> expected = Arrays.asList("Umang", "Test");
            System.out.println(observedEntriesName);
            assertEquals(observedEntriesName, expected);
        } catch (Exception e){
            System.out.println("Exception in insertRowConstraintTest" + e.getMessage());
            assertEquals("String Value at column: 0 violates constraint STRING_LENGTH_20 for value abcdeghijklmnopqrstuvwxyzab", e.getMessage());
        }
    }

    @Test
    void deleteRowTest(){
        try{
            dbService.createTable("Employee", Arrays.asList(
                    new TableHeader("Name", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20),
                    new TableHeader("Age", TableHeader.TYPES.INT, false, TableHeader.Constraint.INT_RANGE_1024)
            ));

            int firstrowId = dbService.insertEntryinTable("Employee", Arrays.asList("Random Name", 26));
            int secondrowId = dbService.insertEntryinTable("Employee", Arrays.asList("Test Name", 30));

            List<TableRow> allRows = dbService.getAllTableRowOfTable("Employee");
            assertEquals(2, allRows.size());


            dbService.deleteRowInTableFromRowId("Employee", 2);
            List<TableRow> allRowsAfterDelete = dbService.getAllTableRowOfTable("Employee");
            assertEquals(1, allRowsAfterDelete.size());

            List<String> observedEntriesName = allRowsAfterDelete.stream().map(TableRow::getColumnValues)
                    .map(hm -> hm.get("Name"))
                    .map(o -> (String)o).collect(Collectors.toList());
            System.out.println(observedEntriesName);
        } catch (Exception e){
            assertEquals(0,1);
        }
    }

}
