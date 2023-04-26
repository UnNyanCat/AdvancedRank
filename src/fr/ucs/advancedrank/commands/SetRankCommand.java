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

import java.io.IOException;

public class SetRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("advancedrank.set")) {
                player.sendMessage("§cErreur: Vous n'avez pas la permission.");
                return false;
            }

            if (args.length == 0 || args.length == 1) {
                player.sendMessage("§cErreur: /setrank <pseudo> <rank>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage("§cErreur: Ce joueur n'existe pas ou n'est pas connecté(e).");
                return false;
            }

            ConfigFile configuration = ConfigFile.PLAYERS;
            FileConfiguration config = configuration.getConfig();
            int id = Integer.parseInt(args[1]);
            Rank rank = RankAPI.getPlayerRank(id);

            try {
                config.set(target.getName(), rank.getPower());
                configuration.save(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            player.sendMessage("§6» §fVous avez bien changé le grade de §c" + target.getName() + " §fen: " + rank.getDisplay());
            target.sendMessage("§6» §fVotre grade a été changé en: " + rank.getDisplay());
        }
        return false;
    }
}
