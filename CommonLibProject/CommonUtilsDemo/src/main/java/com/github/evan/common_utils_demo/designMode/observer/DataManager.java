package com.github.evan.common_utils_demo.designMode.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2018/9/15.
 */
public class DataManager implements IObservable<List<Person>> {
    private static final DataManager mInstance = new DataManager();
    public static DataManager getInstance(){
        return mInstance;
    }

    private DataManager() {
    }

    private List<Person> mData = new ArrayList<>();
    private List<IObserver<List<Person>>> mObservers = new ArrayList<>();

    public void addPerson(Person person){
        mData.add(person);
        List<Person> persons = dataCopy();
        notifyObservers(persons);
    }

    public void removePerson(String name){
        Person p = null;
        for (int i = 0; i < mData.size(); i++) {
            Person person = mData.get(i);
            if(person.getName().equals(name)){
                p  =  person;
                break;
            }
        }

        mData.remove(p);
        List<Person> persons = dataCopy();
        notifyObservers(persons);
    }

    public void update(Person person){
        int willUpdatingIndex = -1;
        for (int i = 0; i < mData.size(); i++) {
            Person p = mData.get(i);
            if(p.getName().equals(person.getName())){
                willUpdatingIndex = i;
                break;
            }
        }

        if(willUpdatingIndex >= 0){
            mData.set(willUpdatingIndex, person);
            List<Person> persons = dataCopy();
            notifyObservers(persons);
        }
    }

    public Person get(String name){
        Person p = null;
        for (int i = 0; i < mData.size(); i++) {
            Person person = mData.get(i);
            if(person.getName().equals(name)){
                p  =  person;
                break;
            }
        }

        return p;
    }

    private List<Person> dataCopy(){
        if(null != mData){
            List<Person> data = new ArrayList<>(mData.size());
            for (int i = 0; i < mData.size(); i++) {
                Person person = mData.get(i);
                Person p = new Person(person.getName(), person.getAge());
                data.add(p);
            }
            return data;
        }

        return null;
    }

    @Override
    public boolean addObserver(IObserver<List<Person>> observer) {
        return mObservers.add(observer);
    }

    @Override
    public boolean removeObserver(IObserver<List<Person>> observer) {
        return mObservers.remove(observer);
    }

    @Override
    public void notifyObservers(List<Person> persons) {
        for (int i = 0; i < mObservers.size(); i++) {
            IObserver<List<Person>> listIObserver = mObservers.get(i);
            listIObserver.onUpdate(persons);
        }
    }

    @Override
    public void clearAllObservers() {
        mObservers.clear();
    }
}
