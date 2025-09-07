package com.krisped;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SimpleCreationDialog extends JDialog {
    private final PresetService presetService;
    private final JComboBox<String> whatCombo;
    private final JComboBox<String> targetCombo;
    private final JCheckBox hullBox;
    private final JCheckBox outlineBox;
    private final JCheckBox tileBox;
    private final JCheckBox minimapBox;
    private final JButton okButton;

    public SimpleCreationDialog(Window owner, PresetService presetService) {
        super(owner, "Simple Creation", ModalityType.APPLICATION_MODAL);
        this.presetService = presetService;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Choose what to create:");
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(new EmptyBorder(0, 12, 12, 12));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(4, 0, 4, 8);

        // Row: What to create
        content.add(new JLabel("Type:"), gc);
        gc.gridx = 1;
        whatCombo = new JComboBox<>(new String[]{"Highlight"});
        whatCombo.setSelectedIndex(0);
        content.add(whatCombo, gc);

        // Row: Target
        gc.gridx = 0; gc.gridy++;
        content.add(new JLabel("Target:"), gc);
        gc.gridx = 1;
        targetCombo = new JComboBox<>(new String[]{"Player", "Local Player"});
        targetCombo.setSelectedIndex(0);
        content.add(targetCombo, gc);

        // Row: Highlight options
        gc.gridx = 0; gc.gridy++; gc.gridwidth = 2;
        JPanel highlightOptions = new JPanel(new GridBagLayout());
        highlightOptions.setBorder(BorderFactory.createTitledBorder("Highlight types"));
        GridBagConstraints ogc = new GridBagConstraints();
        ogc.gridx = 0; ogc.gridy = 0; ogc.anchor = GridBagConstraints.WEST; ogc.insets = new Insets(2, 8, 2, 8);
        hullBox = new JCheckBox("Hull");
        highlightOptions.add(hullBox, ogc);
        ogc.gridx = 1;
        outlineBox = new JCheckBox("Outline");
        highlightOptions.add(outlineBox, ogc);
        ogc.gridx = 0; ogc.gridy++;
        tileBox = new JCheckBox("Tile");
        highlightOptions.add(tileBox, ogc);
        ogc.gridx = 1;
        minimapBox = new JCheckBox("Minimap");
        highlightOptions.add(minimapBox, ogc);

        content.add(highlightOptions, gc);
        add(content, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        okButton = new JButton("OK");
        okButton.addActionListener(e -> onConfirm());
        cancelButton.addActionListener(e -> dispose());
        buttons.add(cancelButton);
        buttons.add(okButton);
        add(buttons, BorderLayout.SOUTH);

        // Validation: OK enabled when any checkbox is selected
        Runnable validate = this::updateOkEnabled;
        hullBox.addActionListener(e -> validate.run());
        outlineBox.addActionListener(e -> validate.run());
        tileBox.addActionListener(e -> validate.run());
        minimapBox.addActionListener(e -> validate.run());
        updateOkEnabled();

        setResizable(false);
    }

    private void updateOkEnabled() {
        boolean any = hullBox.isSelected() || outlineBox.isSelected() || tileBox.isSelected() || minimapBox.isSelected();
        okButton.setEnabled(any);
    }

    private void onConfirm() {
        String what = (String) whatCombo.getSelectedItem();
        String target = (String) targetCombo.getSelectedItem();
        List<String> options = new ArrayList<>();
        if (hullBox.isSelected()) options.add("Hull");
        if (outlineBox.isSelected()) options.add("Outline");
        if (tileBox.isSelected()) options.add("Tile");
        if (minimapBox.isSelected()) options.add("Minimap");

        String displayName = what + " - " + target + (options.isEmpty() ? "" : " [" + String.join(", ", options) + "]");
        if (presetService != null) {
            presetService.addPreset(what, target, options, displayName);
        }
        dispose();
    }
}
