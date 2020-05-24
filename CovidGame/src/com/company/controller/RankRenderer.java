package com.company.controller;

import javax.swing.*;
import java.awt.*;

public class RankRenderer implements ListCellRenderer<String> {


    @Override
    public Component getListCellRendererComponent(JList<? extends String> jList, String value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = new JLabel(value);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        label.setOpaque(true);
        label.setForeground(Color.BLUE);
        return label;
    }
}
