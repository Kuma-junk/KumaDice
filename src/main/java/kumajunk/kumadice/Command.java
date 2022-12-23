package kumajunk.kumadice;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;
import static kumajunk.kumadice.KumaDice.*;

public class Command implements Listener, CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c[KumaDice]コンソールからは実行できません!!");
            return true;
        }
        if (label.equalsIgnoreCase("kdice")) {
            if (!sender.hasPermission("kdice.player")) {
                sender.sendMessage("§c[KumaDice]You don't have permission!");
                return true;
            }
            switch (args.length) {
                case 1 -> {
                    if (sender.hasPermission("kdice.op")) {
                        if (args[0].equals("on")) {
                            ondice = true;
                            sender.sendMessage(pluginTitle + "§7§lonにしました");
                            return true;
                        }
                        if (args[0].equals("off")) {
                            ondice = false;
                            sender.sendMessage(pluginTitle + "§7§loffにしました");
                            return true;
                        }
                    }
                    if (ondice) {
                        sender.sendMessage(pluginTitle + "現在KumaDiceはOFFです...");
                        return true;
                    }
                    if (dodice) {
                        sender.sendMessage(pluginTitle + "現在実行中です...");
                        return true;
                    }
                    if (args[0].length() >= 10) {
                        sender.sendMessage(pluginTitle + "§7§l数値は10億以上に設定できません");
                        return true;
                    }
                    boolean isNumeric = args[0].matches("^[+-]?[0-9]+$");
                    if (!isNumeric) {
                        sender.sendMessage(pluginTitle + "§7§lサイコロの面数は1以上の整数にしてください");
                    }
                    int dicestakes = parseInt(args[0]);
                    if (dicestakes <= 1) {
                        sender.sendMessage("§l[§e§lMan10Dice§f§l]§r§c§lダイスの面数は1以上の整数にしてください");
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("§k§k§k" + pluginTitle + sender.getName() + "§7§lがサイコロを振りました...§k§k§k");
                    }
                    swinger = (Player) sender;
                    stakes = dicestakes;
                    dice = 1;
                    Dice dice = new Dice();
                    dice.start();
                    return true;
                }
                case 2 -> {
                    if (ondice) {
                        sender.sendMessage(pluginTitle + "現在KumaDiceはOFFです...");
                        return true;
                    }
                    if (dodice) {
                        sender.sendMessage(pluginTitle + "現在実行中です...");
                        return true;
                    }
                    if (args[0].length() >= 10) {
                        sender.sendMessage("§l[§e§lMan10Dice§f§l]§r§c§l数値は10億以上に設定できません");
                        return true;
                    }
                    boolean isNumeric = args[0].matches("^[+-]?[0-9]+$");
                    if (!isNumeric) {
                        sender.sendMessage(pluginTitle + "§7§lサイコロの面数は1以上の整数にしてください");
                    }
                    int dicestakes = parseInt(args[0]);
                    if (dicestakes <= 1) {
                        sender.sendMessage("§l[§e§lMan10Dice§f§l]§r§c§lダイスの面数は1以上の整数にしてください");
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("§k§k§k" + pluginTitle + sender.getName() + "§7§lがサイコロを振りました...§k§k§k");
                    }
                    swinger = (Player) sender;
                    stakes = dicestakes;
                    dice = 1;
                    Dice dice = new Dice();
                    dice.start();
                    return true;
                }
                default -> {
                    sender.sendMessage(pluginTitle + "§7§l/kdice [面の数] [サイコロの数]");
                    if (sender.hasPermission("kdice.op")) {
                        sender.sendMessage(pluginTitle + "§7§l/kdice on : KumaDiceをONにします");
                        sender.sendMessage(pluginTitle + "§7§l/kdice off : KumaDiceをOFFにします");
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args)
    {
        if (command.getName().equalsIgnoreCase("kdice"))
        {
            if (!ondice)
            {
                return null;
            }
            if (args.length == 1)
            {
                if (args[0].length() == 0)
                {
                    return Collections.singletonList("[面の数]");
                }
            }
            if (args.length == 2)
            {
                if (args[1].length() == 0)
                {
                    return Collections.singletonList("[サイコロの数]");
                }
            }
        }
        return null;
    }

}
