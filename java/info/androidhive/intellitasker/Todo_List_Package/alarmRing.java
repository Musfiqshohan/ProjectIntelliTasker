package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class alarmRing extends BroadcastReceiver { //This class will start playing a ringtone/ Audio


    public String SayIt;


    public void onReceive(Context context, Intent intent) {





        SayIt= TaskStringClass.SpeakitLoud;  //This text has to be spoken as ringtone
        int code= intent.getIntExtra("requestCode", 1);
        Toast.makeText(context,"alarm  "+SayIt+"  xx", Toast.LENGTH_LONG).show();
        Vibrator v=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(100000);

        TextToSpeechClass t= new TextToSpeechClass(SayIt); // Text is spoken

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_add_black_24dp)
                        .setContentTitle("alarm")
                        .setContentText(SayIt);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

       // MediaPlayer mp=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI); // Or default alarm
       // mp.start();

    }
}
