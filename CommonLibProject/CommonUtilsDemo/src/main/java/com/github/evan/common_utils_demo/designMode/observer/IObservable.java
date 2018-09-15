package com.github.evan.common_utils_demo.designMode.observer;

/**
 * Created by Evan on 2018/9/15.
 */
public interface IObservable<Data> {

    boolean addObserver(IObserver<Data> observer);

    boolean removeObserver(IObserver<Data> observer);

    void notifyObservers(Data data);

    void clearAllObservers();
}
