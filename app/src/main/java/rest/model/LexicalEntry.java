package rest.model;

import java.util.ArrayList;

/**
 * Created by aliomidi on 7/8/17.
 */

public class LexicalEntry {
    String lexicalCategory;
    ArrayList<Entry> entries;

    public String getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(String lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }
}
