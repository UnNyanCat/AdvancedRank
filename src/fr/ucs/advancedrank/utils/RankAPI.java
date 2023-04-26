package fr.ucs.advancedrank.utils;

import fr.ucs.advancedrank.Main;
import fr.ucs.advancedrank.config.ConfigFile;
import fr.ucs.advancedrank.ranks.Rank;

import java.util.List;
import java.util.Map;

public class RankAPI {
    public static Map<String, Rank> getRanks() {
        return Main.getInstance().getRankManager().getRanks();
    }

    public static Rank getPlayerRank(int power) {
        final Rank[] r = new Rank[1];
        getRanks().forEach((rName, rank) -> {
            if(rank.getPower() == power) r[0] = rank;
        });
        return r[0];
    }

    public static Rank getPlayerRank(String name) {
        final Rank[] r = new Rank[1];
        getRanks().forEach((rName, rank) -> {
            if(rName == name) r[0] = rank;
        });
        return r[0];
    }
}
