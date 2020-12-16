package com.hzwc.intelligent.lock.model.http.stomp;


/**
 * Created by naik on 05.05.16.
 */
public interface ConnectionProvider {

    /**
     * Subscribe this for receive stomp messages
     */
    io.reactivex.Observable<String> messages();

    /**
     * Sending stomp messages via you ConnectionProvider.
     * onError if not connected or error detected will be called, or onCompleted id sending started
     * TODO: send messages with ACK
     */
    io.reactivex.Completable send(String stompMessage);

    /**
     * Subscribe this for receive #LifecycleEvent events
     */
    io.reactivex.Observable<LifecycleEvent> lifecycle();

    /**
     * Disconnects from server. This is basically StaffPark Callable.
     * Automatically emits Lifecycle.CLOSE
     */
    io.reactivex.Completable disconnect();

    io.reactivex.Completable setHeartbeat(int ms);
}
