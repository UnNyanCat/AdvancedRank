package fr.ucs.advancedrank.ranks;

import fr.ucs.advancedrank.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankManager {
    public final Map<String, Rank> ranks = new HashMap<>();

    public void addRank(Rank rank) {
        if (ranks.containsValue(rank)) {
            Main.getInstance().getLogger().info("§cError: Rank already exists");
            return;
        }

        ranks.put(rank.getName(), rank);
    }

    public Map<String, Rank> getRanks() {
        return ranks;
    }
}
