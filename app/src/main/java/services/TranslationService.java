package services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import helpers.DBHelper;
import utils.Util;

/**
 * Created by aomidi on 7/7/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TranslationService extends JobService {

    public static final String EXTRA_NONE_TRANSLATED_LIST = "ExtraNoneTranslatedList";

    @Override
    public boolean onStartJob(JobParameters params) {

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ArrayList<String> wordList = dbHelper.getNoneTranslatedList();
        if(wordList != null && !wordList.isEmpty()) {
            Intent workerIntent = new Intent(getApplicationContext(), TranslationIntentService.class);
            workerIntent.putStringArrayListExtra(EXTRA_NONE_TRANSLATED_LIST, wordList);
            getApplicationContext().startService(workerIntent);
            Util.scheduleJob(getApplicationContext()); // reSchedule
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
