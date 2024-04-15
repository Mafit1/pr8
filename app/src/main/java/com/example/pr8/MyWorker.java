package com.example.pr8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MyWorker extends Worker {

    static class Answer {
        public String fileSizeBytes;
        public String url;
        public Answer(){

        }
    }


    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            URL url = new URL("https://random.dog/woof.json");

            URLConnection urlConnection = url.openConnection();

            HttpsURLConnection connection;
            connection = (HttpsURLConnection) urlConnection;

            Gson gson = new Gson();

            Scanner scanner = new Scanner(connection.getInputStream());
            Answer answer = gson.fromJson(scanner.nextLine(), Answer.class);

            URL newurl = new URL(answer.url);

            Log.d("FFF", newurl.toString());
            Data data = new Data.Builder().putString("key1", newurl.toString()).build();

            return Result.success(data);

        } catch (IOException e) {
            return Result.failure();
        }
    }
}
