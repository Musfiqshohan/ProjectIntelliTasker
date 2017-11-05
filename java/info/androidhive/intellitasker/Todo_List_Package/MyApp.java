package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.app.Application;
import android.content.Context;

/**
 * Created by musfiq on 10/11/17.
 */
public class MyApp extends Application { //for getting Context of current activity
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}