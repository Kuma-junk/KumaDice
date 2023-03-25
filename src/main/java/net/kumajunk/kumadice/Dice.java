package net.kumajunk.kumadice;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

import static net.kumajunk.kumadice.KumaDice.*;

public class Dice extends Thread {
    @Override
    public void run() {
        doDice = true;
        try{
            sleep(3000);
        }catch (InterruptedException e) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!dissablePlayers.contains(player.getUniqueId())) {
                    player.sendMessage(pluginTitle + "§7§lエラーが発生しました");
                }
            }
            e.printStackTrace();
            return;
        }
        Random diceRandom = new Random();
        if (dice == 1) {
            int outnumber = diceRandom.nextInt(stakes) + 1;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!dissablePlayers.contains(player.getUniqueId())) {
                    player.sendMessage(pluginTitle + swinger.getName() + "§7§lは" + stakes + "§7§l面のサイコロを振って" + outnumber + "§7§lを出しました!");
                }
            }
        }
        if (dice >= 2) {
            for (int i =0 ; i < dice ; i++) {
                int outnumber = diceRandom.nextInt(stakes) + 1;
                for (Player player:Bukkit.getOnlinePlayers()) {
                    if (!dissablePlayers.contains(player.getUniqueId())){
                        player.sendMessage(pluginTitle + swinger.getName() + "§7§lは" + stakes + "§7§l面のサイコロを振って" + outnumber + "§7§lを出しました!");
                    }
                }
            }
        }
        doDice = false;
    }
}


