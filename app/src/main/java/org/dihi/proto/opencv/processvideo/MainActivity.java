package org.dihi.proto.opencv.processvideo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    public static final String SOURCE_FILE = "face2.avi";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Video processing started.", Snackbar.LENGTH_LONG).show();

                InputStream in = null;
                OutputStream out = null;
                File videoFile = new File(getExternalFilesDir(null), SOURCE_FILE);
                if (!videoFile.exists() || videoFile.length() == 0) {
                    try {
                        try {
                            in = getAssets().open(SOURCE_FILE);
                        } catch (IOException e) {
                            Timber.wtf(e, "failed to open asset");
                        }
                        try {
                            out = new FileOutputStream(videoFile);
                        } catch (FileNotFoundException e) {
                            Timber.wtf(e, "failed to open %s", videoFile.getAbsolutePath());
                        }
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            Timber.d("writing %d", len);
                            out.write(buffer, 0, len);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (in != null) in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (out != null) out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Timber.d("%s exists and has %d bytes", videoFile.getAbsolutePath(), videoFile.length());
                    try {
                        Timber.d("abs-path = %s", videoFile.getAbsolutePath());
                        Timber.d("can-path = %s", videoFile.getCanonicalPath());
                        Timber.d("path     = %s", videoFile.getPath());
                    } catch (IOException e) {
                        Timber.wtf(e);
                    }
                }
                VideoProcessor.getInstance().process(videoFile.getPath());
            }
        });

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
