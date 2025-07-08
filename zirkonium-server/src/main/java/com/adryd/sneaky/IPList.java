package com.adryd.sneaky;

import java.io.File;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static me.elephant1214.zirkonium.Zirkonium.LOGGER;

public final class IPList {
    public static final IPList INSTANCE = new IPList();
    private static final File FILE = Paths.get("allowed-ping-ips.csv").toFile();
    private static final long THIRTY_DAYS_MS = 2592000000L;

    private final Map<String, Long> ipList = new HashMap<>();
    private boolean loaded = false;

    private IPList() {
    }

    public void loadFromFile() {
        try {
            String data = Files.readString(FILE.toPath());
            if (data.isBlank()) {
                return;
            }
            String[] lines = data.split("\n");
            for (String line : lines) {
                if (line.startsWith("#")) {
                    continue;
                }
                String[] split = line.split(",");
                if (split.length == 2) {
                    this.ipList.put(split[0], Long.parseLong(split[1]));
                }
            }
        } catch (NoSuchFileException e) {
            this.loaded = true;
            this.saveToFile(true);
        } catch (IOException | NumberFormatException e) {
            LOGGER.warn("[Sneaky] Failed to read allowed IPs list:", e);
        }
        this.loaded = true;
    }

    public void saveToFile(boolean newFile) {
        // Prevent overwriting the file with nothing if we haven't loaded it yet
        if (!this.loaded) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("# This file contains allowed IP addresses and their last login date in miliseconds.\n");
        builder.append("# Setting lastLoginDate to 0 makes an IP never expire.\n");
        builder.append("#ipAddress,lastLoginMiliseconds\n");

        if (newFile) {
            // Allow localhost to ping
            // https://github.com/itzg/docker-minecraft-server/issues/2312
            builder.append("127.0.0.1,0\n");
            builder.append("0:0:0:0:0:0:0:1%0,0\n");
        }

        long writeTime = System.currentTimeMillis();
        this.ipList.forEach((ip, lastLogin) -> {
            if (lastLogin == 0 || writeTime - lastLogin < THIRTY_DAYS_MS) {
                builder.append(ip);
                builder.append(",");
                builder.append(lastLogin);
                builder.append("\n");
            }
        });
        try {
            Files.writeString(FILE.toPath(), builder.toString());
        } catch (IOException e) {
            LOGGER.error("[Sneaky] Failed to save allowed IPs list:", e);
        }
    }

    public void addToIPList(SocketAddress address) {
        this.ipList.put(Sneaky.stringifyAddress(address), System.currentTimeMillis());
    }

    public boolean canPing(SocketAddress address) {
        String ip = Sneaky.stringifyAddress(address);
        if (this.ipList.containsKey(ip)) {
            if (System.currentTimeMillis() - this.ipList.get(ip) < THIRTY_DAYS_MS || this.ipList.get(ip) == 0) {
                return true;
            }
            this.ipList.remove(ip);
        }
        return false;
    }
}
