package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextToSpeechClass {


        TextToSpeech t1;

        private TextToSpeech mTextToSpeech;

        TextToSpeechClass(String sayIt)
        {

                sayText(MyApp.getContext(),sayIt);

        }


        public void sayText(Context context, final String message) { //This class mainly speaks the message

                mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                                try {
                                        if (mTextToSpeech != null && status == TextToSpeech.SUCCESS) {
                                                mTextToSpeech.setLanguage(Locale.US);
                                                mTextToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null);
                                        }
                                } catch (Exception ex) {
                                        System.out.print("Error handling TextToSpeech GCM notification " + ex.getMessage());
                                }
                        }
                });
        }


}
