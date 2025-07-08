package me.elephant1214.zirkonium.configuration;

import com.adryd.sneaky.IPList;
import com.google.common.base.Throwables;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static me.elephant1214.zirkonium.Zirkonium.LOGGER;

public final class ZirkoniumConfig {
    private static final String HEADER = """
        This is the main configuration file for Zirkonium.
        There isn't much right now, but there will be more in the future. Changes here can affect gameplay on
        your server negatively depending on what you want and don't want in your server, configure carefully.
        """;
    private static File CONFIG_FILE;
    public static YamlConfiguration config;
    public static int version;

    public static void init(File configFile) {
        CONFIG_FILE = configFile;
        config = new YamlConfiguration();
        try {
            config.load(CONFIG_FILE);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            LOGGER.error("Could not load zirkonium.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }
        config.options().header(HEADER);
        config.options().copyDefaults(true);

        version = getInt("config-version", 1);
        set("config-version", 1);

        readConfig();
    }

    static void readConfig() {
        sneakySettings();

        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            LOGGER.error("Could not save {}", CONFIG_FILE, ex);
        }
    }

    private static void set(@NotNull String path, @NotNull Object val) {
        config.addDefault(path, val);
        config.set(path, val);
    }

    private static boolean getBoolean(@NotNull String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static int getInt(@NotNull String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    public static boolean sneaky = false;
    public static boolean hideServerPingData = true;
    public static boolean onlyHidePlayerList = false;
    public static boolean dontLogClientDisconnects = false;
    public static boolean dontLogServerDisconnects = false;
    public static boolean disableAllPingsUntilLogin = false;
    public static boolean disableLegacyQuery = true;
    public static boolean disableConnectionsForBannedIps = false;

    private static void sneakySettings() {
        IPList.INSTANCE.loadFromFile();

        sneaky = getBoolean("sneaky.enabled", sneaky);
        hideServerPingData = getBoolean("sneaky.hide-server-ping-data", hideServerPingData);
        onlyHidePlayerList = getBoolean("sneaky.only-hide-player-list", onlyHidePlayerList);
        dontLogClientDisconnects = getBoolean("sneaky.dont-log-client-disconnects", dontLogClientDisconnects);
        dontLogServerDisconnects = getBoolean("sneaky.dont-log-server-disconnects", dontLogServerDisconnects);
        disableAllPingsUntilLogin = getBoolean("sneaky.disable-all-pings-until-login", disableAllPingsUntilLogin);
        disableLegacyQuery = getBoolean("sneaky.disable-legacy-query", disableLegacyQuery);
        disableConnectionsForBannedIps = getBoolean("sneaky.disable-connections-for-banned-ips", disableConnectionsForBannedIps);
    }
}
