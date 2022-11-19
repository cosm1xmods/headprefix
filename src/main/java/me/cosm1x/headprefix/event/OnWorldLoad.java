package me.cosm1x.headprefix.event;

import me.cosm1x.headprefix.headprefix.HeadPrefix;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class OnWorldLoad {
    public static void register() {
        ServerWorldEvents.LOAD.register(OnWorldLoad::onWorldLoad);
    }

    public static void onWorldLoad(MinecraftServer server, ServerWorld world) {
        HeadPrefix hp = world.getPersistentStateManager().getOrCreate(HeadPrefix::readNbt, HeadPrefix::getHeadPrefixInstance, "headprefix");
        hp.markDirty();
    }
}
