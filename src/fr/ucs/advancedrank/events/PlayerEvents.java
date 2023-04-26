package fr.ucs.advancedrank.events;

import fr.ucs.advancedrank.Main;
import fr.ucs.advancedrank.config.ConfigFile;
import fr.ucs.advancedrank.ranks.Rank;
import fr.ucs.advancedrank.ranks.RankManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        RankManager rankManager = Main.getInstance().getRankManager();
        ConfigFile configuration = ConfigFile.PLAYERS;
        FileConfiguration config = configuration.getConfig();

        Rank rank = rankManager.getRanks().get(config.getInt(player.getName()));

        event.setMessage(rank.getDisplay() + player.getName() + " » " + message);
    }
}
