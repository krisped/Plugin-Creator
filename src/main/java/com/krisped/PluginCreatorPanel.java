package com.krisped;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.runelite.client.ui.PluginPanel;

public class PluginCreatorPanel extends PluginPanel {
	public PluginCreatorPanel() {
		super(false); // no wrap
		setLayout(new BorderLayout());
		JLabel title = new JLabel("Plugin Creator", SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
	}
}
