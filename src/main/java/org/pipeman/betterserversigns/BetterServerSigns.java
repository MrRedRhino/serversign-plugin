package org.pipeman.betterserversigns;

import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockVector;

import java.util.HashMap;
import java.util.List;

public final class BetterServerSigns extends JavaPlugin {
    public static HashMap<String, BlockVector> signs = new HashMap<>();
    public static HashMap<String, List<String>> texts = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new SendPlayer());
        Bukkit.getPluginManager().registerEvents(new SendPlayer(), this);
        loadConfig(getConfig());
        this.getLogger().info("HELLO!");

        getServerInfo();
        // Plugin startup logic
    }

    static void getServerInfo() {
        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        for (String server : signs.keySet()) {
            if (p == null) continue;
            Util.requestPlayerCount(server, p);
        }
        Bukkit.getScheduler().runTaskLater(getPlugin(), BetterServerSigns::getServerInfo, 20L);
    }

    public void loadConfig(FileConfiguration config) {
        config.options().copyDefaults(true);
        saveConfig();

        for (String path : config.getKeys(false)) {
            BlockVector pos = new BlockVector();
            pos.setX(config.getInt(path + ".pos.x"));
            pos.setY(config.getInt(path + ".pos.y"));
            pos.setZ(config.getInt(path + ".pos.z"));
            signs.put(path, pos);
            texts.put(path, config.getStringList(path + ".text"));

            this.getLogger().info("Loaded sign " + path + " at position " + pos + " with text: " + texts.get(path));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() {
        return BetterServerSigns.getPlugin(BetterServerSigns.class);
    }
}
