package me.cosm1x.headprefix.networking;

import java.util.HashMap;
import java.util.function.IntFunction;

import me.cosm1x.headprefix.HeadPrefixModClient;
import me.cosm1x.headprefix.headprefix.HeadPrefix;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class HeadPrefixC2S {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(PacketRegistry.HEAD_PREFIX_UPDATE, HeadPrefixC2S::teamUpdateHandle);
    }

    public static void teamUpdateHandle(
        MinecraftClient minecraftClient,
        ClientPlayNetworkHandler cpnh,
        PacketByteBuf buf,
        PacketSender packetSender) {
            IntFunction<HashMap<String, Text>> intFunction = PacketByteBuf.getMaxValidator(HashMap::new, 128);
            HeadPrefix hp =  new HeadPrefix(buf.readMap(intFunction, PacketByteBuf::readString, PacketByteBuf::readText));
            HeadPrefixModClient.headPrefix = hp;
            
    }
}
