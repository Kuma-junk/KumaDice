package kumajunk.kumadice;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;
import static kumajunk.kumadice.KumaDice.*;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§l[KumaDice]コンソールからは実行できません!!");
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
                            if (onDice) {
                                sender.sendMessage(pluginTitle + "§c§lすでにonになっています!");
                                return true;
                            }
                            onDice = true;
                            sender.sendMessage(pluginTitle + "§7§lonにしました");
                            return true;
                        }
                        if (args[0].equals("off")) {
                            if (!onDice) {
                                sender.sendMessage(pluginTitle + "§c§lすでにoffになっています!");
                                return true;
                            }
                            onDice = false;
                            sender.sendMessage(pluginTitle + "§7§loffにしました");
                            return true;
                        }
                    }
                    Player playerId = (Player) sender;
                    if (args[0].equals("hide")) {
                        if (dissablePlayers.contains(playerId.getUniqueId())) {
                            return true;
                        }
                        else {
                            dissablePlayers.add(playerId.getUniqueId());
                            sender.sendMessage(pluginTitle + "§7§l非表示にしました");
                            return true;
                        }
                    }
                    if (args[0].equals("show")) {
                        if (dissablePlayers.contains(playerId.getUniqueId())) {
                            dissablePlayers.remove(playerId.getUniqueId());
                            sender.sendMessage(pluginTitle + "§7§l表示しました");
                            return true;
                        }
                        else {
                            return true;
                        }
                    }
                    if (!onDice) {
                        sender.sendMessage(pluginTitle + "§c§l現在KumaDiceはOFFです!");
                        return true;
                    }
                    if (doDice) {
                        sender.sendMessage(pluginTitle + "§7§l現在実行中です...");
                        return true;
                    }
                    if (args[0].length() >= 10) {
                        sender.sendMessage(pluginTitle + "§c§l数値は10億以上に設定できません!");
                        return true;
                    }
                    boolean isNumeric = args[0].matches("^[+-]?[0-9]+$");
                    if (!isNumeric) {
                        sender.sendMessage(pluginTitle + "§c§lサイコロの面数は2以上の整数にしてください!");
                    }
                    int diceStakes = parseInt(args[0]);
                    if (diceStakes <= 1) {
                        sender.sendMessage(pluginTitle+"§c§lサイコロの面数は2以上の整数にしてください!");
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (!dissablePlayers.contains(player.getUniqueId())) {
                            player.sendMessage("§k§k§k" + pluginTitle + sender.getName() + "§7§lがサイコロを振りました...§k§k§k");
                        }
                    }
                    swinger = (Player) sender;
                    stakes = diceStakes;
                    dice = 1;
                    Dice dice = new Dice();
                    dice.start();
                    return true;
                }
                case 2 -> {
                    if (!onDice) {
                        sender.sendMessage(pluginTitle + "§c§l現在KumaDiceはOFFです!");
                        return true;
                    }
                    if (doDice) {
                        sender.sendMessage(pluginTitle + "§7§l現在実行中です...");
                        return true;
                    }
                    if (args[0].length() >= 10) {
                        sender.sendMessage(pluginTitle + "§c§l数値は10億以上に設定できません");
                        return true;
                    }
                    boolean isNumeric = args[0].matches("^[+-]?[0-9]+$");
                    if (!isNumeric) {
                        sender.sendMessage(pluginTitle + "§c§lサイコロの面数は2以上の整数にしてください!");
                    }
                    int diceStakes = parseInt(args[0]);
                    if (diceStakes <= 1) {
                        sender.sendMessage(pluginTitle + "§c§lサイコロの面数は2以上の整数にしてください!");
                        return true;
                    }
                    isNumeric = args[1].matches("^[+-]?[0-9]+$");
                    if (!isNumeric) {
                        sender.sendMessage(pluginTitle + "§c§lサイコロの個数は1以上の整数にしてください!");
                    }
                    int numberOfDice = parseInt(args[1]);
                    if (numberOfDice <= 1) {
                        sender.sendMessage(pluginTitle + "§c§lサイコロの個数は1以上の整数にしてください!");
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("§k§k§k" + pluginTitle + sender.getName() + "§7§lが" + numberOfDice + "個サイコロを振りました...§k§k§k");
                    }
                    swinger = (Player) sender;
                    stakes = diceStakes;
                    dice = numberOfDice;
                    Dice dice = new Dice();
                    dice.start();
                    return true;
                }
                default -> {
                    sender.sendMessage(pluginTitle + "§7§l/kdice [面の数]");
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
            if (!onDice)
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
