package rest.model;

import java.util.ArrayList;

/**
 * Created by aliomidi on 7/8/17.
 */

public class RetrieveEntry {
    ArrayList<HeadwordEntry> results;

    public ArrayList<HeadwordEntry> getResults() {
        return results;
    }

    public void setResults(ArrayList<HeadwordEntry> results) {
        this.results = results;
    }
}
