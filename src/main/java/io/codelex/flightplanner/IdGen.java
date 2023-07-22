package io.codelex.flightplanner;

public class IdGen {
    private static long id = 0;

    public static long getNextId() {
        return ++id;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        IdGen.id = id;
    }
}
