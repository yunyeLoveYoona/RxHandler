package cgym.cgym.net.rxhandler.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yunYe on 2016/6/17.
 */
public class RxHandler {
    private Handler.Callback mCallback;

    public RxHandler(Handler.Callback mCallback) {
        this.mCallback = mCallback;
    }

    public RxHandler() {
    }

    /**
     * 默认主线程中传递Message
     *
     * @param message
     */
    public void sendMessage(Message message) {
        Observable.just(message).subscribe(new Action1<Message>() {
            @Override
            public void call(Message message) {
                if (mCallback != null) {
                    mCallback.handleMessage(message);
                }
            }
        });
    }

    /**
     * 在新的线程中传递message
     *
     * @param message
     */
    public void sendMessageOnNewThread(Message message) {
        Observable.just(message).observeOn(Schedulers.newThread()).subscribe(new Action1<Message>() {
            @Override
            public void call(Message message) {
                if (mCallback != null) {
                    mCallback.handleMessage(message);
                }
            }
        });
    }


    public void post(Runnable runnable) {
        Observable.just(runnable).subscribe(new Action1<Runnable>() {
            @Override
            public void call(Runnable runnable) {
                runnable.run();
            }
        });
    }

    public void postOnNewThread(Runnable runnable) {
        Observable.just(runnable).observeOn(Schedulers.newThread()).subscribe(new Action1<Runnable>() {
            @Override
            public void call(Runnable runnable) {
                runnable.run();
            }
        });
    }

    public void postDelayed(Runnable r, final long delayMillis) {
        Observable.just(r).map(new Func1<Runnable, Runnable>() {
            @Override
            public Runnable call(Runnable runnable) {
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return runnable;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Action1<Runnable>() {
            @Override
            public void call(Runnable runnable) {
                runnable.run();
            }
        });
    }

    public void postDelayedOnNewThread(Runnable r, final long delayMillis) {
        Observable.just(r).map(new Func1<Runnable, Runnable>() {
            @Override
            public Runnable call(Runnable runnable) {
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return runnable;
            }
        }).observeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread()).subscribe(new Action1<Runnable>() {
            @Override
            public void call(Runnable runnable) {
                runnable.run();
            }
        });
    }

    public void postRunnables(Runnable... runnable) {
        Observable.from(runnable).subscribe(new Action1<Runnable>() {
            @Override
            public void call(Runnable runnable) {
                runnable.run();
            }
        });
    }

    public void postRunnablesOnNewThread(Runnable... runnable) {
        Observable.from(runnable).observeOn(Schedulers.newThread()).subscribe(new Action1<Runnable>() {
            @Override
            public void call(Runnable runnable) {
                runnable.run();
            }
        });
    }
}
