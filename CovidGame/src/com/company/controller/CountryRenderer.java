package com.company.controller;

import com.company.model.Country;

import javax.swing.*;
import java.awt.*;

public class CountryRenderer implements ListCellRenderer<Country> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Country> list, Country value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel jLabel = new JLabel(value.getcName());


        jLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));

        jLabel.setOpaque(true);
        if (isSelected)
            jLabel.setBackground(Color.CYAN);
        else
            jLabel.setBackground(Color.WHITE);

        return jLabel;


    }
}
