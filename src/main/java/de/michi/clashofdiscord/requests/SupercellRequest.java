package de.michi.clashofdiscord.requests;

import de.michi.clashofdiscord.ClashOfDiscord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Michi on 09.06.2019.
 */
public class SupercellRequest {



    private String tag;
    private RequestType rtype;



    public SupercellRequest(RequestType type, String identifier) {
        this.tag = identifier.replace("#", "%23");
        this.rtype = type;
    }

    public String execute() {
        String url = "https://api.clashofclans.com/v1/";
        switch (rtype) {
            case CLAN:
                url += "clans/" + tag;
                break;
            case CLANMEMBERS:
                url += "clans/" + tag + "/members";
                break;
            case CURRENTWAR:
                url += "clans/" + tag + "/currentwar";
                break;
            case PLAYER:
                url += "players/" + tag;
                break;
        }
        StringBuffer response = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + ClashOfDiscord.apikey);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
