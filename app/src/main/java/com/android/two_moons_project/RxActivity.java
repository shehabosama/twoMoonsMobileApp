package com.android.two_moons_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


public class RxActivity extends AppCompatActivity {

    private static final String TAG = "main";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        //this is the cold observable example (Observable)
        /**
         Observable<Long> cold = Observable.intervalRange(0,5,0 ,1,TimeUnit.SECONDS);
        cold.subscribe(i -> Log.e(TAG, "onCreate: output student one"+i ));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cold.subscribe(i-> Log.e(TAG, "onCreate: output student two"+i ));
        **/

        //this is cold observable converted to  the hot observable example (ConnectableObservable)

        /**
         ConnectableObservable<Long> cold = ConnectableObservable.intervalRange(0,5,0 ,1,TimeUnit.SECONDS).publish();
        cold.connect();
        cold.subscribe(i -> Log.e(TAG, "onCreate: output student one"+i ));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cold.subscribe(i-> Log.e(TAG, "onCreate: output student two"+i ));
         **/

        //this is the hot observable from scratch (PublishSubject)

        /**
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(i-> Log.e(TAG, "onCreate: output first student "+i ));
        subject.onNext("a");
        sleep(1000);

        subject.onNext("b");
        sleep(1000);
        subject.onNext("c");
        sleep(1000);
        subject.onNext("d");
        sleep(1000);
        subject.subscribe(i-> Log.e(TAG, "onCreate: output second student "+i ));

        subject.onNext("e");
        sleep(1000);
        subject.onNext("f");
        sleep(1000);
        subject.onNext("g");
        sleep(1000);
         **/

        //this is the hot observable but the observer need to know the last information from the data first (BehaviorSubject)

        /**
        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(i-> Log.e(TAG, "onCreate: output first student "+i ));
        subject.onNext("a");
        sleep(1000);

        subject.onNext("b");
        sleep(1000);
        subject.onNext("c");
        sleep(1000);
        subject.onNext("d");
        sleep(1000);
        subject.subscribe(i-> Log.e(TAG, "onCreate: output second student "+i ));

        subject.onNext("e");
        sleep(1000);
        subject.onNext("f");
        sleep(1000);
        subject.onNext("g");
        sleep(1000);
         **/

        //this is the hot observables example about the replay all data for a new late observer (ReplaySubject)
       /**
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(i-> Log.e(TAG, "onCreate: output first student "+i ));
        subject.onNext("a");
        sleep(1000);

        subject.onNext("b");
        sleep(1000);
        subject.onNext("c");
        sleep(1000);
        subject.onNext("d");
        sleep(1000);
        subject.subscribe(i-> Log.e(TAG, "onCreate: output second student "+i ));

        subject.onNext("e");
        sleep(1000);
        subject.onNext("f");
        sleep(1000);
        subject.onNext("g");
        sleep(1000);
        **/

       //this is the hot observable example here the observer care about just last element added (AsyncSubject)
       /**
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(i-> Log.e(TAG, "onCreate: output first student "+i ));
        subject.onNext("a");
        sleep(1000);

        subject.onNext("b");
        sleep(1000);
        subject.onNext("c");
        sleep(1000);
        subject.onNext("d");
        sleep(1000);
        subject.subscribe(i-> Log.e(TAG, "onCreate: output second student "+i ));

        subject.onNext("e");
        sleep(1000);
        subject.onNext("f");
        sleep(1000);
        subject.onNext("g");
        sleep(1000);
        subject.onComplete();
        */




       //first factories method (Create)

        /**
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                for(int i=0;i<5 ;i++){
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        });
         **/
        //second factories method (just)
       /** Observable.just(0,1,2,3,4,5)
                .subscribeOn(Schedulers.io())
                .doOnNext(c-> Log.d(TAG, "upStream: " +c + " current thread  "+Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .subscribe(o-> Log.d(TAG, "DownStream "+o+" current thread " +Thread.currentThread().getName()));
        */

       Observable.create(new ObservableOnSubscribe<Object>() {
           @Override
           public void subscribe(@NonNull ObservableEmitter<Object> emitter) {
                EditText editText =findViewById(R.id.editText);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() !=0)
                        emitter.onNext(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
           }
       }).doOnNext(c-> Log.d(TAG, "onCreate: upStream" + c))
               .map(new Function<Object, Object>() {
                   @Override
                   public Object apply(Object o) {
                       return Integer.parseInt(o.toString())*2;
                   }
               })
               .debounce(2,TimeUnit.SECONDS)
               .distinctUntilChanged()//to make sure if you preview search or not if we are searched about it we will not search again
               .filter(c->!c.toString().equals("24")) // if the result from the observable equals 24 don't accept it
               .flatMap(new Function<Object, ObservableSource<?>>() {// anther Observer
                   @Override
                   public ObservableSource<?> apply(Object o) {
                       return sendDataToAPI(o.toString());
                   }
               })
               .subscribe(o ->{Log.d(TAG, "onCreate: hit api with downStream "+o);

               } );

       //third factories method (from array)

        /**
        Integer[] list = new Integer[5];
        list[0] = 0;
        list[1] = 1;
        list[2] = 2;
        list[3] = 3;
        list[4] = 4;
        Observable observable = Observable.fromArray(list);

         //with repeat repeat make the array print depend the count of replies

        Integer[] list = new Integer[5];
        list[0] = 0;
        list[1] = 1;
        list[2] = 2;
        list[3] = 3;
        list[4] = 4;
        Observable observable = Observable.fromArray(list).repeat(2);
         */


        //third factories method (range)
        /** Observable observable = Observable.range(0,6);*/

        //third factories method (timer)
       //  Observable observable = Observable.timer(3,TimeUnit.SECONDS);


//        Observer observer = new Observer() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Log.e(TAG, "onSubscribe: " );
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.e(TAG, "onNext: "+o );
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.e(TAG, "onError: "+e.getMessage() );
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "onComplete: " );
//            }
//        };
//        observable.subscribe(observer);


    }

    public Observable sendDataToAPI(String data){
        Observable observable = Observable.just("Send Data To API"+data);
        observable.subscribe(c-> Log.d(TAG, "sendDataToAPI: "+c));

     return observable;
    }
    public void sleep (int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
