package net.kumajunk.kumadice;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public final class KumaDice extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {
    public static boolean doDice = false;
    public static boolean onDice;
    public static String pluginTitle = "§f§l[§e§lKumaDice]§r";
    public static Player swinger;
    public static int dice;
    public static int stakes;
    public static int maxDice;
    public static List<UUID> dissablePlayers =new ArrayList<>();
    public static net.kumajunk.kumadice.KumaDice KumaDice;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        KumaDice = this;

        getCommand("kdice").setExecutor(new Commands());

        onDice = KumaDice.getConfig().getBoolean("OnDice");
        maxDice = KumaDice.getConfig().getInt("MaxDice");
    }

}