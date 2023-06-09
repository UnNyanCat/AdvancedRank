package fr.ucs.advancedrank.commands;

import fr.ucs.advancedrank.Main;
import fr.ucs.advancedrank.ranks.Rank;
import fr.ucs.advancedrank.utils.ItemCreator;
import fr.ucs.advancedrank.utils.RankAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("advancedrank.seelist")) {
                player.sendMessage("§cErreur: Vous n'avez pas la permission de faire ceci.");
                return false;
            }

            Inventory inv = Bukkit.createInventory(null, 27, "§6§l» §fListe des grades");

            List<Integer> ranks = new ArrayList<>();
            Main.getInstance().getRankManager().getRanks().forEach((name, rank) -> {
                ranks.add(rank.getPower());
            });

            Collections.sort(ranks);

            for (Integer power : ranks) {
                Rank rank = RankAPI.getPlayerRank(power);
                inv.addItem(new ItemCreator(Material.SKULL_ITEM)
                        .setName("§6§l» §f" + rank.getDisplay())
                        .setTableauLores(new String[]{
                                "",
                                "§6§l» §fPower: §b" + rank.getPower(),
                                "§6§l» §fName: §b" + rank.getName(),
                                "",
                        })
                        .getItem());
            }

            player.openInventory(inv);
        }
        return false;
    }
}
