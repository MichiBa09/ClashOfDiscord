package de.michi.clashofdiscord.objects;


import de.michi.clashofdiscord.requests.RequestType;
import de.michi.clashofdiscord.requests.SupercellRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Michi on 09.06.2019.
 */
public class Player {


    private String tag;
    private String name;
    private String clanName;
    private String clanTag;
    private String townhall;
    private String role;
    private int clanLevel;
    private int level;
    private int trophies;
    private int bestTrophies;
    private int warStars;
    private int donations;
    private int donationsReceived;


    public Player(String tag) {
        this.tag = tag;
    }

    public Player initialize() {

        String response = new SupercellRequest(RequestType.PLAYER, tag).execute();
        JSONParser parser = new JSONParser();

        if (response != null) {
            try {
                JSONObject jobj = (JSONObject) parser.parse(response.toString());

                townhall = String.valueOf((Long) jobj.get("townHallLevel"));
                JSONObject clanObj = (JSONObject) jobj.get("clan");
                this.name = (String) jobj.get("name");
                this.role = (String) jobj.get("role");
                this.level = Integer.valueOf(String.valueOf((Long) jobj.get("expLevel")));
                this.trophies = Integer.valueOf(String.valueOf((Long) jobj.get("trophies")));
                this.bestTrophies = Integer.valueOf(String.valueOf((Long) jobj.get("bestTrophies")));
                this.warStars = Integer.valueOf(String.valueOf((Long) jobj.get("warStars")));
                this.donations = Integer.valueOf(String.valueOf((Long) jobj.get("donations")));
                this.donationsReceived = Integer.valueOf(String.valueOf((Long) jobj.get("donationsReceived")));


                if (clanObj != null) {
                    this.clanName = (String) clanObj.get("name");
                    this.clanTag = (String) clanObj.get("tag");
                    this.clanLevel = Integer.parseInt(String.valueOf((Long) clanObj.get("clanLevel")));
                } else {
                    this.clanName = null;
                    this.clanTag = null;

                }
            } catch (ParseException e) {
                throw new NullPointerException("Couldn't fetch data for this Player.");

            }
        } else {
            return null;
        }
        return this;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getClanName() {
        return clanName;
    }

    public String getClanTag() {
        return clanTag;
    }

    public String getTownhall() {
        return townhall;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public int getLevel() {
        return level;
    }

    public int getTrophies() {
        return trophies;
    }

    public int getBestTrophies() {
        return bestTrophies;
    }

    public int getWarStars() {
        return warStars;
    }

    public String getRole() {
        return role;
    }

    public int getDonations() {
        return donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }
}
