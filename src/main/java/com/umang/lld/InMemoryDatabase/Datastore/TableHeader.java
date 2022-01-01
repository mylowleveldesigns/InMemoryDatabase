package com.umang.lld.InMemoryDatabase.Datastore;

public class TableHeader {
    public static interface TYPES{
        public final String STRING = "STRING";
        public final String INT = "INT";
    }
    public static enum Constraint {NONE, STRING_LENGTH_20, INT_RANGE_1024};

    String name;
    String type;
    Boolean canNull;
    Constraint constraint;

    public TableHeader(String name, String type, Boolean canNull, Constraint constraint) {
        this.name = name;
        this.type = type;
        this.canNull = canNull;
        this.constraint = constraint;
    }
}
