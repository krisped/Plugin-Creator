package com.krisped;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.PluginPanel;

public class PluginCreatorPanel extends PluginPanel {
    private final PresetService presetService;
    private final JButton createNewBtn;
    private final JPanel center;

    public PluginCreatorPanel(PresetService presetService) {
        this.presetService = presetService;
        setLayout(new BorderLayout());

        // Center panel
        center = new JPanel();

        // Create New button
        createNewBtn = new JButton("Create New");
        createNewBtn.addActionListener(e -> {
            Window owner = SwingUtilities.getWindowAncestor(this);
            CreateNewDialog dialog = new CreateNewDialog(owner, presetService);
            dialog.setLocationRelativeTo(null); // center on screen
            dialog.setVisible(true);
        });

        center.add(createNewBtn);
        add(center, BorderLayout.CENTER);
    }
}
