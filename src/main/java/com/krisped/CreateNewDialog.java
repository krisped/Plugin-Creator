package com.krisped;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CreateNewDialog extends JDialog {
    private final PresetService presetService;

    public CreateNewDialog(Window owner, PresetService presetService) {
        super(owner, "Create New", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        this.presetService = presetService;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Choose type:");
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        JButton simpleBtn = new JButton("Simple");
        JButton advancedBtn = new JButton("Advanced");

        simpleBtn.addActionListener(ev -> {
            dispose();
            SimpleCreationDialog simple = new SimpleCreationDialog(owner, presetService);
            simple.pack();
            simple.setLocationRelativeTo(null);
            simple.setVisible(true);
        });
        advancedBtn.addActionListener(ev -> {
            // TODO: implement Advanced flow
            dispose();
        });

        buttons.add(simpleBtn);
        buttons.add(advancedBtn);
        add(buttons, BorderLayout.CENTER);

        pack();
        setResizable(false);
    }
}
