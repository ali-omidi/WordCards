package services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import helpers.DBHelper;
import rest.model.RetrieveEntry;

/**
 * Created by aomidi on 7/7/2017.
 */

public class TranslationIntentService extends IntentService {

    public TranslationIntentService(){
        super("Worker Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Bundle bundle = intent.getExtras();
        if(bundle == null) return;
        List<String> nonTranslatedList = bundle.getStringArrayList(TranslationService.EXTRA_NONE_TRANSLATED_LIST);


        final String app_id = "806aafc7";
        final String app_key = "8636bcd6d6dadb938a0fe78af2f68947";
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        for(String s: nonTranslatedList){
            try {
                URL url = new URL(dictionaryEntries(s));
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                String translateResult = stringBuilder.toString();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String finalJson="";
                RetrieveEntry retrieveEntry = gson.fromJson(translateResult, RetrieveEntry.class);
                if (retrieveEntry != null && retrieveEntry.getResults() !=null && !retrieveEntry.getResults().isEmpty() ){
                    if(retrieveEntry.getResults().get(0).getLexicalEntries() != null && !retrieveEntry.getResults().get(0).getLexicalEntries().isEmpty()){
                        //if(retrieveEntry.getResults().get(0).getLexicalEntries().get(0).getEntries() != null && !retrieveEntry.getResults().get(0).getLexicalEntries().get(0).getEntries().isEmpty()){
                           finalJson =  gson.toJson(retrieveEntry.getResults().get(0).getLexicalEntries());
                        //}
                    }
                }
                if (!finalJson.isEmpty()){
                    dbHelper.updateWord(s,finalJson);
                }else {
                    dbHelper.updateWord(s, translateResult);
                }
            }
            catch (Exception e) {
                if(e instanceof FileNotFoundException){
                    dbHelper.updateWord(s, "Not Found!");
                }
                e.printStackTrace();
            }
        }
    }

    private String dictionaryEntries(String word) {
        final String language = "en";
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

}
