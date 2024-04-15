package com.example.pr8;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(MainActivity.this);

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(com.example.pr8.MyWorker.class).build();
        WorkManager.getInstance(this).enqueue(workRequest);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>(){
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            String url = workInfo.getOutputData().getString("key1");

                            Log.d("RRR","Status="+workInfo.getState()+", url="+url);
                       //     if(workInfo.getState().isFinished()) {
                                 Picasso.get().load(url).into(imageView);
                         //   }

                        }
                    }
                );
        setContentView(imageView);
    }
}
