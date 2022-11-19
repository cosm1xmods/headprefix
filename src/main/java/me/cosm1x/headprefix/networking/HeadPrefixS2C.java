package me.cosm1x.headprefix.networking;

import me.cosm1x.headprefix.headprefix.HeadPrefix;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.world.World;

public class HeadPrefixS2C {
    
    public static void register() {    
        ServerPlayConnectionEvents.JOIN.register(HeadPrefixS2C::sendHeadPrefixMapOnJoin);
        // ServerLoginNetworking.registerGlobalReceiver(HeadPrefixS2C.HANDSHAKE, HeadPrefixS2C::handleHandshakeReply);
    }

    public static void sendHeadPrefixMapOnJoin(
        ServerPlayNetworkHandler serverLoginNetworkHandler, 
        PacketSender packetSender,
        MinecraftServer minecraftServer ) {
            HeadPrefix hp = minecraftServer.getWorld(World.OVERWORLD).getPersistentStateManager().getOrCreate(HeadPrefix::readNbt, HeadPrefix::getHeadPrefixInstance, "headprefix");
            packetSender.sendPacket(PacketRegistry.HEAD_PREFIX_UPDATE, hp.createBuf());
    }
}
