package fr.ucs.advancedrank.commands;

import fr.ucs.advancedrank.Main;
import fr.ucs.advancedrank.config.ConfigFile;
import fr.ucs.advancedrank.ranks.Rank;
import fr.ucs.advancedrank.ranks.RankManager;
import fr.ucs.advancedrank.utils.RankAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SeeRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                ConfigFile configuration = ConfigFile.PLAYERS;
                FileConfiguration config = configuration.getConfig();
                Rank rank = RankAPI.getPlayerRank(config.getInt(player.getName()));

                player.sendMessage("§6» §fVoici votre grade: " + rank.getName());
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null || !target.isOnline()) {
                    player.sendMessage("§cErreur: Ce joueur n'existe pas ou n'est pas connecté(e).");
                    return false;
                }
                ConfigFile configuration = ConfigFile.PLAYERS;
                FileConfiguration config = configuration.getConfig();

                Rank rank = RankAPI.getPlayerRank(config.getInt(target.getName()));

                player.sendMessage("§6» §fVoici le grade de §c" + target.getName() + "§f: " + rank.getName());
            }
        }
        return false;
    }
}
