package me.cosm1x.headprefix.headprefix;

import java.util.HashMap;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;

public class HeadPrefix {
    private static HashMap<String, Text> headPrefixMap = new HashMap<String, Text>();

    public static HashMap<String, Text> getHeadPrefixMap() {
        return headPrefixMap;
    }

    public static Text getHeadPrefix(String team) {
        return headPrefixMap.get(team);
    }
    
    public static Text getHeadPrefix(Team team) {
        return headPrefixMap.get(team.getName());
    }

    public static void setHeadPrefixMap(HashMap<String, Text> map) {
        headPrefixMap = map;
    }

    public static void setHeadPrefix(String team, Text headPrefix) {
        headPrefixMap.put(team, headPrefix);
    }

    public static void setHeadPrefix(Team team, Text headPrefix) {
        headPrefixMap.put(team.getName(), headPrefix);
    }

    public static PacketByteBuf createBuf() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeMap(HeadPrefix.getHeadPrefixMap(), PacketByteBuf::writeString, PacketByteBuf::writeText);
        return buf;
    }
}
