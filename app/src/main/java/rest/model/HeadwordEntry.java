package rest.model;

import java.util.ArrayList;

/**
 * Created by aliomidi on 7/8/17.
 */

public class HeadwordEntry {
    String id;
    String language;
    ArrayList<LexicalEntry> lexicalEntries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<LexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public void setLexicalEntries(ArrayList<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
    }
}
