package de.michi.clashofdiscord;

/**
 * Created by Michi on 09.06.2019.
 */
public class ClashOfDiscord {
    public static String supercellKey;
    public static ClashOfDiscord instance;
    public String prefix;

    public ClashOfDiscord(String supercellKey, String prefix) {
        instance = this;
        this.supercellKey = supercellKey;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

}
