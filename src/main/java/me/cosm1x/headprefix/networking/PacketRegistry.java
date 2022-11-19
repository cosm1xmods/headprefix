package me.cosm1x.headprefix.networking;

import static me.cosm1x.headprefix.HeadPrefixMod.*;
import net.minecraft.util.Identifier;

public class PacketRegistry {
    public static final Identifier HEAD_PREFIX_UPDATE = initIdentifer("head_prefix_update");
    public static void register() {
        HeadPrefixS2C.register();
    }
    public static void registerClient() {
        HeadPrefixS2C.register();
        HeadPrefixC2S.register();
    }
}
