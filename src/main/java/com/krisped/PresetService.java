package com.krisped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.runelite.client.config.ConfigManager;

public class PresetService {
    private static final String GROUP = "plugincreator";
    private static final String COUNT_KEY = "preset.count";

    private final ConfigManager configManager;
    private final List<Preset> presets = new ArrayList<>();
    private final List<Runnable> listeners = new ArrayList<>();

    public PresetService(ConfigManager configManager) {
        this.configManager = configManager;
        load();
    }

    public List<Preset> getPresets() {
        return Collections.unmodifiableList(presets);
    }

    public void addChangeListener(Runnable r) {
        if (r != null) listeners.add(r);
    }

    public void removeChangeListener(Runnable r) {
        listeners.remove(r);
    }

    public Preset addPreset(String type, String target, List<String> options, String displayName) {
        String id = UUID.randomUUID().toString();
        Preset p = new Preset(id, type, target, options, displayName);
        presets.add(p);
        save();
        notifyListeners();
        return p;
    }

    private void notifyListeners() {
        for (Runnable r : new ArrayList<>(listeners)) {
            try { r.run(); } catch (Exception ignored) {}
        }
    }

    private void load() {
        presets.clear();
        String countStr = configManager.getConfiguration(GROUP, COUNT_KEY);
        int count = 0;
        try { if (countStr != null) count = Integer.parseInt(countStr); } catch (NumberFormatException ignored) {}
        for (int i = 0; i < count; i++) {
            String id = get("preset." + i + ".id");
            String type = get("preset." + i + ".type");
            String target = get("preset." + i + ".target");
            String optionsStr = get("preset." + i + ".options");
            String name = get("preset." + i + ".name");
            if (id == null || type == null || target == null || optionsStr == null || name == null) {
                continue; // skip corrupted entries
            }
            List<String> options = new ArrayList<>();
            if (!optionsStr.isEmpty()) {
                for (String part : optionsStr.split(",")) {
                    String opt = part.trim();
                    if (!opt.isEmpty()) options.add(opt);
                }
            }
            presets.add(new Preset(id, type, target, options, name));
        }
    }

    private void save() {
        // Write count first
        configManager.setConfiguration(GROUP, COUNT_KEY, String.valueOf(presets.size()));
        for (int i = 0; i < presets.size(); i++) {
            Preset p = presets.get(i);
            set("preset." + i + ".id", p.getId());
            set("preset." + i + ".type", p.getType());
            set("preset." + i + ".target", p.getTarget());
            set("preset." + i + ".options", String.join(",", p.getOptions()));
            set("preset." + i + ".name", p.getDisplayName());
        }
    }

    private String get(String key) {
        return configManager.getConfiguration(GROUP, key);
    }

    private void set(String key, String value) {
        configManager.setConfiguration(GROUP, key, value);
    }
}

