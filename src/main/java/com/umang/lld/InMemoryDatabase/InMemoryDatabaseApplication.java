package com.umang.lld.InMemoryDatabase;

import com.umang.lld.InMemoryDatabase.Datastore.TableHeader;
import com.umang.lld.InMemoryDatabase.Services.DatabaseManagerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class InMemoryDatabaseApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(InMemoryDatabaseApplication.class, args);
		DatabaseManagerService dbService = context.getBean(DatabaseManagerService.class);

		System.out.println("In Memory Database Started ......");
		try{
			dbService.createTable("Employee", Arrays.asList(
					new TableHeader("Name", TableHeader.TYPES.STRING, false, TableHeader.Constraint.STRING_LENGTH_20),
					new TableHeader("Age", TableHeader.TYPES.INT, false, TableHeader.Constraint.INT_RANGE_1024)
			));

			dbService.insertEntryinTable("Employee", Arrays.asList("Umang", 24));

			System.out.println(dbService.getAllTableNames());
			System.out.println(dbService.getAllTableRowOfTable("Employee"));
		} catch (Exception e){
			System.out.print(e);
		}
		while(true){

		}
	}

}
