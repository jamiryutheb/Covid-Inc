package com.company.view;

import com.company.controller.CountryRenderer;
import com.company.model.Country;
import com.company.model.CountryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CountryListFrame extends JFrame {

    List<Country> countryList;
    JScrollPane scrollPane;
    JList list;
    Country selectedC;
    JButton select;
    JList<Country> jList;
    public CountryListFrame(List<Country> countryList) {

        list = new JList();
        this.countryList = countryList;

        jList = new JList<>();
        scrollPane = new JScrollPane(jList);
        CountryModel model = new CountryModel(countryList);
        jList.setModel(model);
        jList.setCellRenderer(new CountryRenderer());

        select = new JButton("SELECT");
        JPanel jPanel = new JPanel(new GridLayout(1, 1));
        jPanel.add(select);






        setLayout(new BorderLayout());
        add(jPanel, BorderLayout.PAGE_END);
        add(scrollPane, BorderLayout.CENTER);

        setTitle("Country List");
        setSize(200,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public Country getCountry() {
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jList.getSelectedIndex();
                selectedC = countryList.get(i);
                dispose();
            }
        });
        return selectedC;
    }
}
