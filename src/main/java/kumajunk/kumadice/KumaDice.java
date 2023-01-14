package kumajunk.kumadice;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class KumaDice extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {
    public static boolean doDice = false;
    public static boolean onDice;
    public static String pluginTitle;
    public static Player swinger;
    public static int dice;
    public static int stakes;
    public static List<UUID> dissablePlayers = new ArrayList<>();
    public static kumajunk.kumadice.KumaDice KumaDice;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        KumaDice = this;

        saveDefaultConfig();

        pluginTitle = "§f§l[§e§lKumaDice]§r";

        getCommand("kdice").setExecutor(new Commands());

        onDice = KumaDice.getConfig().getBoolean("OnDice");
    }
}