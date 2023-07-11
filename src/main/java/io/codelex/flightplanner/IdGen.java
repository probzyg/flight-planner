package io.codelex.flightplanner;

public class IdGen {
    private static int id = 0;

    public static int getNextId() {
        return ++id;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        IdGen.id = id;
    }
}
