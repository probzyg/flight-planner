package io.codelex.flightplanner;

public class IdGen {
    private static long id = 0;

    public static long getNextId() {
        return ++id;
    }
}
