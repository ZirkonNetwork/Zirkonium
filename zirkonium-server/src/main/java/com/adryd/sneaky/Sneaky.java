package com.adryd.sneaky;

import me.elephant1214.zirkonium.configuration.ZirkoniumConfig;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class Sneaky {
    public static String stringifyAddress(@NotNull SocketAddress address) {
        String string = ((InetSocketAddress) address).getAddress().getHostAddress();
        if (string.startsWith("/")) {
            string = string.substring(1);
        }
        return string;
    }

    public static boolean shouldAllowConnection(@NotNull SocketAddress address) {
        return !ZirkoniumConfig.disableConnectionsForBannedIps || !MinecraftServer.getServer().getPlayerList().getIpBans().isBanned(address);
    }
}
