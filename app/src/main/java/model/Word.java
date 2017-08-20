package model;

/**
 * Created by aomidi on 6/30/2017.
 */

public class Word {
    private long id;
    private String word;
    private String description;
    private long captureDate;

    public Word(long id, String w, String desc, long cDate){
        this.id = id;
        this.word = w;
        this.description = desc;
        this.captureDate = cDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(long captureDate) {
        this.captureDate = captureDate;
    }
}
