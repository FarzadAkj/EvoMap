package ir.evoteam.evomap;

/**
 * Created by shahr on 4/14/2017.
 */
public class test {
    private static test ourInstance = new test();

    public static test getInstance() {
        return ourInstance;
    }

    private test() {
    }
}
