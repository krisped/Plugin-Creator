package com.krisped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Preset {
    private final String id;
    private final String type; // e.g., "Highlight"
    private final String target; // e.g., "Player", "Local Player"
    private final List<String> options; // e.g., ["Hull", "Outline"]
    private final String displayName; // Render-friendly name

    public Preset(String id, String type, String target, List<String> options, String displayName) {
        this.id = id;
        this.type = type;
        this.target = target;
        this.options = new ArrayList<>(options);
        this.displayName = displayName;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getTarget() { return target; }
    public List<String> getOptions() { return Collections.unmodifiableList(options); }
    public String getDisplayName() { return displayName; }
}

