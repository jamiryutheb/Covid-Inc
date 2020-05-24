package com.company.model;

import javax.swing.*;
import java.util.List;

public class CountryModel extends AbstractListModel<Country> {

    private List<Country> list;

    public CountryModel(List<Country> list){
        this.list = list;
    }

    public void add(Country c , int index){
        list.add(index, c);
        fireIntervalAdded(this, index, index);
    }

    public void add(Country c){
        list.add(getSize(), c);
    }

    public void remove(int index){
        list.remove(index);
        fireIntervalRemoved(this, index, index);
    }


    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Country getElementAt(int index) {
        return list.get(index);
    }
}
