package me.cosm1x.headprefix.headprefix;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;


import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.world.PersistentState;

public class HeadPrefix extends PersistentState{
    private HashMap<String, Text> headPrefixMap;

    public HeadPrefix(HashMap<String, Text> hashMap) {
        this.headPrefixMap = hashMap;
    }

    public static HeadPrefix getHeadPrefixInstance() {
        HeadPrefix hp = new HeadPrefix(new HashMap<String, Text>());
        hp.markDirty();
        return hp;
    }
    
    public Map<String, Text> getHeadPrefixMap() {
        return headPrefixMap;
    }

    public Text getHeadPrefix(String team) {
        return headPrefixMap.get(team);
    }
    
    public Text getHeadPrefix(Team team) {
        return headPrefixMap.get(team.getName());
    }

    public void setHeadPrefixMap(HashMap<String, Text> map) {
        this.headPrefixMap = map;
    }

    public void setHeadPrefix(String team, Text headPrefix) {
        this.headPrefixMap.put(team, headPrefix);
    }

    public void setHeadPrefix(Team team, Text headPrefix) {
        this.headPrefixMap.put(team.getName(), headPrefix);
    }

    public void removeHeadPrefix(Team team) {
        this.headPrefixMap.remove(team.getName());
    }
    
    public PacketByteBuf createBuf() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeMap(this.getHeadPrefixMap(), PacketByteBuf::writeString, PacketByteBuf::writeText);
        return buf;
    }
    
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound prefixes = new NbtCompound();
        for (String key : this.getHeadPrefixMap().keySet()) {
            prefixes.putString(key, Text.Serializer.toJson(this.headPrefixMap.get(key)));
        }
        nbt.put("HeadPrefixes", prefixes);
        return nbt;
    }

    public static HeadPrefix readNbt(NbtCompound nbt) {
        NbtCompound prefixes = nbt.getCompound("HeadPrefixes");
        HashMap<String, Text> prefixesMap = new HashMap<String, Text>();
        for (String key : prefixes.getKeys()) {
            prefixesMap.put(key, Text.Serializer.fromJson(prefixes.getString(key)));
        }
        HeadPrefix headPrefixInstance = new HeadPrefix(prefixesMap);

        return headPrefixInstance;
    }

}