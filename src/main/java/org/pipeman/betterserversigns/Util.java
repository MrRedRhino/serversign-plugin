package org.pipeman.betterserversigns;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class Util {
    static void sendPlayer(String server, Player player) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("Connect");
        dataOutput.writeUTF(server);
        player.sendPluginMessage(BetterServerSigns.getPlugin(), "BungeeCord", dataOutput.toByteArray());
    }

    static void requestPlayerCount(String server, Player player) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("PlayerCount");
        dataOutput.writeUTF(server);
        player.sendPluginMessage(BetterServerSigns.getPlugin(), "BungeeCord", dataOutput.toByteArray());
    }
}
