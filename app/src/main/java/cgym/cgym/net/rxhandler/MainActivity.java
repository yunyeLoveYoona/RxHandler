package cgym.cgym.net.rxhandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cgym.cgym.net.rxhandler.handler.RxHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RxHandler rxHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxHandler = new RxHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String name = Thread.currentThread().getName();
                final String alert = new StringBuffer("getMessage ").append(msg.obj.toString()).append(" on ").append(name).toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, alert, Toast.LENGTH_SHORT).show();
                    }
                });

                return false;
            }
        });
        findViewById(R.id.postRun).setOnClickListener(this);
        findViewById(R.id.sendMessage).setOnClickListener(this);
        findViewById(R.id.sendMessageOnNewThread).setOnClickListener(this);
        findViewById(R.id.postRunAfter5s).setOnClickListener(this);
        findViewById(R.id.postRunOnNewThread).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessage:
                Message message = new Message();
                message.obj = "sendMessage";
                rxHandler.sendMessage(message);
                break;
            case R.id.postRun:
                rxHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, new StringBuffer("postRunOn ").append(Thread.currentThread().getName()).toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.postRunAfter5s:
                rxHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, new StringBuffer("postRunAfter5sOn ").append(Thread.currentThread().getName()).toString(), Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
                break;
            case R.id.postRunOnNewThread:


                rxHandler.postOnNewThread(new Runnable() {
                    @Override
                    public void run() {
                        final String alert = new StringBuffer("postRunOnNewThread ").append(Thread.currentThread().getName()).toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, alert, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case R.id.sendMessageOnNewThread:
                Message message1 = new Message();
                message1.obj = "sendMessageOnNewThread";
                rxHandler.sendMessageOnNewThread(message1);
                break;

        }
    }
}
