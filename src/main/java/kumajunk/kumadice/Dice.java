package kumajunk.kumadice;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

import static kumajunk.kumadice.KumaDice.*;

public class Dice extends Thread {
    @Override
    public void run() {
        dodice = true;
        try{
            sleep(3000);
        }catch (InterruptedException e) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(pluginTitle+"§7§lエラーが発生しました");
            }
            e.printStackTrace();
            return;
        }
        Random dicerandom = new Random();
        if (dice == 1) {
            int outnumber = dicerandom.nextInt(stakes) + 1;
            for (Player player:Bukkit.getOnlinePlayers()) {
                player.sendMessage(pluginTitle + swinger.getName() +"§7§lは" + stakes + "§7§l面のサイコロを振って" + outnumber + "§7§lを出しました!");
            }
        }
        dodice = false;
    }
}
