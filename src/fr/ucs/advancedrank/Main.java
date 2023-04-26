package fr.ucs.advancedrank;

import fr.ucs.advancedrank.commands.ListRankCommand;
import fr.ucs.advancedrank.commands.SeeRankCommand;
import fr.ucs.advancedrank.commands.SetRankCommand;
import fr.ucs.advancedrank.config.ConfigFile;
import fr.ucs.advancedrank.events.InventoryEvents;
import fr.ucs.advancedrank.events.PlayerEvents;
import fr.ucs.advancedrank.ranks.Rank;
import fr.ucs.advancedrank.ranks.RankManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private RankManager rankManager;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        this.rankManager = new RankManager();
    }

    @Override
    public void onEnable() {
        ConfigFile players = ConfigFile.PLAYERS;
        players.create(getLogger());

        getCommand("setrank").setExecutor(new SetRankCommand());
        getCommand("seerank").setExecutor(new SeeRankCommand());
        getCommand("listrank").setExecutor(new ListRankCommand());

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);

        getRankManager().addRank(new Rank(0, "§7Player", "§7Joueur "));
        getRankManager().addRank(new Rank(1, "§eVIP", "§eVIP "));
        getRankManager().addRank(new Rank(2, "§aHelper", "§aGuide "));
        getRankManager().addRank(new Rank(3, "§bModerator", "§bModo "));
        getRankManager().addRank(new Rank(4, "§6Developer", "§6Dev "));
        getRankManager().addRank(new Rank(5, "§cAdministrator", "§cAdmin "));
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
