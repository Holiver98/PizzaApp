package com.github.holiver98.util;
import java.util.HashSet;

public class ObservableHashSet<T> extends HashSet<T>{
    public interface ObservableHashSetListener{
        void onItemAdded(Object item);
        void onItemRemoved(Object item);
    }

    private ObservableHashSetListener listener;

    @Override
    public boolean add(T item) {
        boolean isSuccessful = super.add(item);
        if(isSuccessful && listener != null){
            listener.onItemAdded(item);
        }
        return isSuccessful;
    }

    @Override
    public boolean remove(Object item) {
        boolean isSuccessful = super.remove(item);
        if(isSuccessful && listener != null){
            listener.onItemRemoved(item);
        }
        return isSuccessful;
    }

    public void setListener(ObservableHashSetListener listener){
        this.listener = listener;
    }
}