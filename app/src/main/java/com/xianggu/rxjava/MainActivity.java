package com.xianggu.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.baseLib.BaseActivity;
import com.android.baseLib.util.PrintLogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {


        /**
         * 创建    被观察者
         * 参数ObservableOnSubscribe<T>可以理解为一个计划表
         * subscribe中的ObservableEmitter<String>对象的Emitter是发射器的意思
         * ObservableEmitter有三种发射的方法，void onNext()-->可以无限调用
         *          void onError(Throwable error)、--->不可以无限调用，只能用一次
         *          void onComplete()--->可以无限调用   与onError()互斥，观察者只能收到一个
         */
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });


        /**
         * 创建观察者
         */
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {      //Disposable 可以在此取消订阅
                PrintLogUtil.log("Observer 观察者  onSubscribe执行 Disposable.dispose()取消订阅");
            }

            @Override
            public void onNext(String s) {               //与被观察者对应
                PrintLogUtil.log("Observer 观察者  onNext:"+s);
            }

            @Override
            public void onError(Throwable e) {           //与被观察者对应
                PrintLogUtil.log("Observer 观察者  onError");
            }

            @Override
            public void onComplete() {                   //与被观察者对应
                PrintLogUtil.log("Observer 观察者  onComplete");
            }
        };


        //被观察者 与 观察者 建立订阅关系
        observable.subscribe(observer);
    }


}
