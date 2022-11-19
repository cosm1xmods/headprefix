package me.cosm1x.headprefix;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.cosm1x.headprefix.config.HeadPrefixConfig;
import me.cosm1x.headprefix.headprefix.HeadPrefix;
import me.cosm1x.headprefix.networking.PacketRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HeadPrefixModClient implements ClientModInitializer{
    public static final String MODID = "headprefix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static HeadPrefix headPrefix = new HeadPrefix(new HashMap<String, Text>());

    @Override
    public void onInitializeClient() {
        PacketRegistry.registerClient();
        AutoConfig.register(HeadPrefixConfig.class, Toml4jConfigSerializer::new);
        LOGGER.info("Initialized!");
    } 
    
    public static Identifier initIdentifer(String path) {
        return new Identifier(MODID, path);
    }
    
}
