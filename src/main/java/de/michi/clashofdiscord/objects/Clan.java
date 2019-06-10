package de.michi.clashofdiscord.objects;

import de.michi.clashofdiscord.requests.RequestType;
import de.michi.clashofdiscord.requests.SupercellRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michi on 09.06.2019.
 */
public class Clan {

    private String tag;
    private List<Player> players;
    private List<String> playerTags;
    private String name;
    private String type;
    private String description;
    private int level;

    public Clan(String clantag) {
        this.tag = clantag;
    }

    public Clan initialize() {
        this.players = new ArrayList<Player>();
        this.playerTags = new ArrayList<String>();
        SupercellRequest sr = new SupercellRequest(RequestType.CLAN, tag);
        String response = sr.execute();
        JSONParser parser = new JSONParser();
        if (response == null) throw new NullPointerException("Couldn't fetch data for this Clan");
        try {
            JSONObject jobj = (JSONObject) parser.parse(response);
            this.name = (String) jobj.get("name");
            this.type = (String) jobj.get("type");
            this.description = (String) jobj.get("description");
            this.level = Integer.valueOf(String.valueOf((Long) jobj.get("clanLevel")));


            JSONArray members = (JSONArray) jobj.get("memberList");

            for (int i = 0; i < members.size(); i++) {
                JSONObject player = (JSONObject) members.get(i);
                playerTags.add((String) player.get("tag"));
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this;
    }


    public String getTag() {
        return tag;
    }

    public List<Player> getPlayers() {

        for(String all: getPlayerTags()) {
            this.players.add(new Player(all).initialize());
        }
        return players;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public List<String> getPlayerTags() { return this.playerTags; }
}
