package kumajunk.kumadice;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class KumaDice extends JavaPlugin {
    public static boolean dodice = false;
    public static boolean ondice;
    public static String pluginTitle;
    public static Player swinger;
    public static int dice;
    public static int stakes;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        getCommand("kdice").setExecutor(new Command());
        pluginTitle = "§f§l[§e§lKuma§fD§0i§fc§0e§f§l]§r";
        saveDefaultConfig();
    }
}
