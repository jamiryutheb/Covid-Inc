package com.company.model;

import javax.swing.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankModel extends AbstractListModel<String> {

    public List<String> list;


    public RankModel(List<String> list){
        this.list = list;
    }

    public void add(String rank , int index){
        list.add(rank);
        fireIntervalAdded(this, index, index);
    }

    public void add(String rank){
        list.add(rank);
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
    public String getElementAt(int i) {
        return list.get(i);
    }

}
