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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class SendPlayer implements Listener, PluginMessageListener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (Tag.SIGNS.isTagged(event.getClickedBlock().getType())) {

            for (String serverName : BetterServerSigns.signs.keySet()) {
                if (BetterServerSigns.signs.get(serverName).toBlockVector()
                        .equals(event.getClickedBlock().getLocation().toVector().toBlockVector())) {
                    Util.sendPlayer(serverName, event.getPlayer());
                }
            }
        }
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        if (in.readUTF().equals("PlayerCount")) {
            String serverName = in.readUTF();
            int playerCount = in.readInt();

            Location signLoc = BetterServerSigns.signs.get(serverName).toLocation(player.getWorld());

            ArrayList<Component> lines = new ArrayList<>(Arrays.asList(
                    Component.text(ChatColor.translateAlternateColorCodes('§', BetterServerSigns.texts.get(serverName).get(0).replace("{}", playerCount + ""))),
                    Component.text(ChatColor.translateAlternateColorCodes('§', BetterServerSigns.texts.get(serverName).get(1).replace("{}", playerCount + ""))),
                    Component.text(ChatColor.translateAlternateColorCodes('§', BetterServerSigns.texts.get(serverName).get(2).replace("{}", playerCount + ""))),
                    Component.text(ChatColor.translateAlternateColorCodes('§', BetterServerSigns.texts.get(serverName).get(3).replace("{}", playerCount + "")))
            ));

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendSignChange(signLoc, lines);
            }
        }
    }
}
