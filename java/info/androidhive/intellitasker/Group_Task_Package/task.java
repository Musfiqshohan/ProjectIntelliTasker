package info.androidhive.intellitasker.Group_Task_Package;

/**
 * Created by zaoad on 10/12/17.
 */

public class task {
    String title,time,id;
    boolean done;

    public task() {
    }

    public task( String title, String time,boolean done,String id) {
        this.id=id;
        this.title = title;
        this.time = time;
        this.done=done;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public boolean isDone() {
        return done;
    }

    public String getTime() {
        return time;
    }
}
