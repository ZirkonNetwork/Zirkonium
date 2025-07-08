package com.adryd.sneaky.util;

import net.minecraft.SharedConstants;
import net.minecraft.server.ServerInfo;
import org.jetbrains.annotations.NotNull;

public final class LegacyPingMetadata implements ServerInfo {
    @Override
    public @NotNull String getMotd() {
        return "A Minecraft Server";
    }

    @Override
    public @NotNull String getServerVersion() {
        return SharedConstants.getCurrentVersion().name();
    }

    @Override
    public int getPlayerCount() {
        return 0;
    }

    @Override
    public int getMaxPlayers() {
        return 20;
    }
}
