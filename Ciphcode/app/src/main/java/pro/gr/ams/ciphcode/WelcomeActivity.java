package pro.gr.ams.ciphcode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    private Thread thread;
    private Boolean firstTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        overridePendingTransition(R.anim.alpha_enter,R.anim.alpha_exit);
        thread=new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2*1000);
                    Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(i);
                    finish();
                }
                catch (Exception ex)
                {}
            }
        };
        thread.start();

        isFirstTime();
    }

    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
            else {
                Intent i = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(i);
            }
        }
        return firstTime;
    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.interrupt();
        finish();
    }
}
