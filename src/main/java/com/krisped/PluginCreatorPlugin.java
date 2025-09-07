package com.krisped;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "[KP] Plugin Creator"
)
public class PluginCreatorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private PluginCreatorConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ConfigManager configManager;

	private NavigationButton navButton;
	private PluginCreatorPanel panel;
	private PresetService presetService;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");

		presetService = new PresetService(configManager);
		panel = new PluginCreatorPanel(presetService);
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/com/krisped/icons/plugincreator.png");

		navButton = NavigationButton.builder()
			.tooltip("Plugin Creator")
			.icon(icon)
			.priority(5)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
		if (clientToolbar != null && navButton != null)
		{
			clientToolbar.removeNavigation(navButton);
			navButton = null;
		}
		panel = null;
		presetService = null;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
    PluginCreatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PluginCreatorConfig.class);
	}
}
