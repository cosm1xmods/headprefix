package me.cosm1x.headprefix;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.cosm1x.headprefix.command.CommandEventRegistry;
import me.cosm1x.headprefix.event.EventRegistry;
import me.cosm1x.headprefix.networking.PacketRegistry;


public class HeadPrefixMod implements ModInitializer {
	public static final String MODID = "headprefix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	@Override
	public void onInitialize() {
		CommandEventRegistry.register();
		PacketRegistry.register();
		EventRegistry.register();
		LOGGER.info("Initialized!");
	}
	
	public static Identifier initIdentifer(String path) {
		return new Identifier(MODID, path);
	}
}
