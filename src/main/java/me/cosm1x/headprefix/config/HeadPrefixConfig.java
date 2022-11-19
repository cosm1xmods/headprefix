package me.cosm1x.headprefix.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "headprefix")
public class HeadPrefixConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean background = false;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 50, min = 10)
    public int height = 10;
}