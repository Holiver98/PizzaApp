package com.github.holiver98.ui;

public class ItemHolderCard<T> extends ItemCard{
    private T item;

    public ItemHolderCard(T item){
        super();
        this.item = item;
    }

    public T getItem(){
        return item;
    }
}
