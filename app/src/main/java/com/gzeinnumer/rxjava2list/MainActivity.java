package com.gzeinnumer.rxjava2list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> data = new ArrayList<>();
        data.add("Data1");
        data.add("Data2");
        data.add("Data3");
        data.add("Data4");
        data.add("Data5");

        final boolean[] first = {true};

        Observable.interval(1000 * 2, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return false;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull Long count) {
                        String now = new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
                        Log.d(TAG, "onNext: "+"_"+now+"_"+count);

                        if (first[0] && count==2){
                            first[0] = false;
                        } else {
                            data.remove(0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

        /**
         2020-12-10 16:18:12.467 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onSubscribe:
         2020-12-10 16:18:14.473 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onNext: _16:18:14_0
         2020-12-10 16:18:16.473 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onNext: _16:18:16_1
         2020-12-10 16:18:18.472 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onNext: _16:18:18_2
         2020-12-10 16:18:20.475 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onNext: _16:18:20_3
         2020-12-10 16:18:22.474 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onNext: _16:18:22_4
         2020-12-10 16:18:24.473 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onNext: _16:18:24_5
         2020-12-10 16:18:24.473 26981-26981/com.gzeinnumer.rxjava2list D/MainActivity_: onComplete:
         */
    }
}