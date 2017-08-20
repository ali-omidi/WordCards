package utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import services.TranslationService;

/**
 * Created by aomidi on 7/7/2017.
 */

public class Util {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleJob(Context ctx){
        ComponentName serviceComponent = new ComponentName(ctx, TranslationService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(2 * 1000); // wait at least
        builder.setOverrideDeadline(30 * 1000); // maximum delay
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not

        JobScheduler jobScheduler = (JobScheduler) ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}
