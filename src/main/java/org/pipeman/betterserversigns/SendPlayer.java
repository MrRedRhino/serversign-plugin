package org.pipeman.betterserversigns;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SendPlayer implements Listener, PluginMessageListener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || !Tag.SIGNS.isTagged(event.getClickedBlock().getType())) return;
        BlockVector clickVector = event.getClickedBlock().getLocation().toVector().toBlockVector();

        for (Map.Entry<String, BlockVector> entry : BetterServerSigns.signs.entrySet()) {
            if (entry.getValue().toBlockVector().equals(clickVector)) {
                Util.sendPlayer(entry.getKey(), event.getPlayer());
                return;
            }
        }
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        if (!in.readUTF().equals("PlayerCount")) return;

        String serverName = in.readUTF();
        String playerCount = String.valueOf(in.readInt());

        List<String> serverTexts = BetterServerSigns.texts.get(serverName);
        List<Component> lines = List.of(
                Component.text(ChatColor.translateAlternateColorCodes('§', serverTexts.get(0).replace("{}", playerCount))),
                Component.text(ChatColor.translateAlternateColorCodes('§', serverTexts.get(1).replace("{}", playerCount))),
                Component.text(ChatColor.translateAlternateColorCodes('§', serverTexts.get(2).replace("{}", playerCount))),
                Component.text(ChatColor.translateAlternateColorCodes('§', serverTexts.get(3).replace("{}", playerCount)))
        );

        Location signLoc = BetterServerSigns.signs.get(serverName).toLocation(player.getWorld());
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendSignChange(signLoc, lines);
        }
    }
}
