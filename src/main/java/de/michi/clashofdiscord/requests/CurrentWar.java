package de.michi.clashofdiscord.requests;

import de.michi.clashofdiscord.ClashOfDiscord;
import de.michi.clashofdiscord.objects.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.toIntExact;

/**
 * Created by Michi on 10.06.2019.
 */
public class CurrentWar {

    private String tag;
    private String state;
    private int teamSize;

    private String clanTag;
    private String clanName;
    private Long stars;
    private Double percentage;

    private String enemyClanTag;
    private String enemyClanName;
    private Long enemyStars;
    private Double enemyPercentage;

    private List<String> players;
    private List<String> enemyPlayers;

    private List<String> playersName;
    private List<String> enemyPlayersName;

    private HashMap<Integer, Integer> townhalls;
    private HashMap<Integer, Integer> enemyTownhalls;

    private Date preparationStartTime;
    private Date startTime;
    private Date endTime;

    public CurrentWar(String clantag) {
        this.tag = clantag;
    }

    public CurrentWar initialize() {


        players = new ArrayList<>();
        enemyPlayers = new ArrayList<>();
        playersName = new ArrayList<>();
        enemyPlayersName = new ArrayList<>();
        townhalls = new HashMap<>();
        enemyTownhalls = new HashMap<>();

        townhalls.put(12, 0);
        townhalls.put(11, 0);
        townhalls.put(10, 0);
        townhalls.put(9, 0);
        townhalls.put(8, 0);
        townhalls.put(7, 0);

        enemyTownhalls.put(12, 0);
        enemyTownhalls.put(11, 0);
        enemyTownhalls.put(10, 0);
        enemyTownhalls.put(9, 0);
        enemyTownhalls.put(8, 0);
        enemyTownhalls.put(7, 0);


        String response = new SupercellRequest(RequestType.CURRENTWAR, tag).execute();
        JSONParser parser = new JSONParser();
        if (response != null) {
            try {
                JSONObject jobj = (JSONObject) parser.parse(response);
                this.state = (String) jobj.get("state");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {
                    this.preparationStartTime = sdf.parse(((String) jobj.get("preparationStartTime")));
                    this.startTime = sdf.parse(((String) jobj.get("startTime")));
                    this.endTime = sdf.parse(((String) jobj.get("endTime")));

                } catch (java.text.ParseException e) {
                    System.out.println(e.getMessage());
                }

                this.teamSize = Integer.parseInt(String.valueOf((Long) jobj.get("teamSize")));
                JSONObject clanObj = (JSONObject) jobj.get("clan");
                this.clanName = (String) clanObj.get("name");
                this.clanTag = (String) clanObj.get("tag");
                this.stars = (Long) clanObj.get("stars");
                this.percentage = (Double) clanObj.get("destructionPercentage");

                JSONObject enemyClanObj = (JSONObject) jobj.get("opponent");
                this.enemyClanName = (String) enemyClanObj.get("name");
                this.enemyClanTag = (String) enemyClanObj.get("tag");
                this.enemyStars = (Long) enemyClanObj.get("stars");
                this.enemyPercentage = (Double) enemyClanObj.get("destructionPercentage");
                JSONArray players = (JSONArray) clanObj.get("members");
                JSONArray enemyPlayers = (JSONArray) enemyClanObj.get("members");

                for (int i = 0; i < players.size(); i++) {
                    JSONObject player = (JSONObject) players.get(i);
                    String tag = (String) player.get("tag");
                    String name = (String) player.get("name");
                    this.playersName.add(name);
                    this.players.add(tag);
                    addTownhall((Long) player.get("townhallLevel"));

                }
                for (int i = 0; i < enemyPlayers.size(); i++) {
                    JSONObject player = (JSONObject) enemyPlayers.get(i);
                    String tag = (String) player.get("tag");
                    String name = (String) player.get("name");
                    this.enemyPlayersName.add(name);
                    this.enemyPlayers.add(tag);
                    addEnemyTownhall((Long) player.get("townhallLevel"));
                }

            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return this;

    }


    private void addTownhall(Long th) {
        Integer townhalllevel = toIntExact(th);
        townhalls.put(townhalllevel, townhalls.get(townhalllevel) + 1);
    }

    private void addEnemyTownhall(Long th) {
        Integer townhalllevel = toIntExact(th);
        enemyTownhalls.put(townhalllevel, enemyTownhalls.get(townhalllevel) + 1);
    }


    public String getClanTag() {
        return clanTag;
    }

    public String getClanName() {
        return clanName;
    }

    public Long getStars() {
        return stars;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getEnemyClanTag() {
        return enemyClanTag;
    }

    public String getEnemyClanName() {
        return enemyClanName;
    }

    public Long getEnemyStars() {
        return enemyStars;
    }

    public Double getEnemyPercentage() {
        return enemyPercentage;
    }

    public String getState() {
        return state;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public List<String> getPlayerTags() {
        return players;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        for (String p : getPlayerTags()) {
            players.add(new Player(p).initialize());
        }

        return players;

    }

    public List<String> getEnemyPlayers() {
        return enemyPlayers;
    }

    public List<String> getPlayersNames() {
        return playersName;
    }

    public List<String> getEnemyPlayersNames() {
        return enemyPlayersName;
    }

    public Date getPreparationStartTime() {
        return preparationStartTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getBreakdown() {
        return townhalls.get(12) + "/" + townhalls.get(11) + "/" + townhalls.get(10) + "/" + townhalls.get(9) + (townhalls.get(8) != 0 ? "/" + townhalls.get(8) : "");
    }

    public String getEnemyBreakdown() {
        return enemyTownhalls.get(12) + "/" + enemyTownhalls.get(11) + "/" + enemyTownhalls.get(10) + "/" + enemyTownhalls.get(9) + (enemyTownhalls.get(8) != 0 ? "/" + enemyTownhalls.get(8) : "");
    }

    public int getTownhallAmount(int townhallLevel) {
        return townhalls.get(townhallLevel) != null ? townhalls.get(townhallLevel) : 0;
    }

    public int getEnemyTownhallAmount(int townhallLevel) {
        return enemyTownhalls.get(townhallLevel) != null ? enemyTownhalls.get(townhallLevel) : 0;
    }
}
