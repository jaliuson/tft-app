package com.example.tftapp;

//import java.net.HttpURLConnection;
import java.net.*;
import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.*;
public class RiotAPIHelper {
    final private String API_KEY = "RGAPI-ccc5d325-95b1-46fe-a80e-19692fb68f0b";
    final private String region = "na1.api.riotgames.com";
    public RiotAPIHelper() {

    }

    public String getPuuid (String summonerNameIngress) {
        try{
            URL url = new URL("https://" + region +"/lol/summoner/v4/summoners/by-name/" + summonerNameIngress + "?api_key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("Response Code : " + (int)conn.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            HashMap<String, String> responseMap = new HashMap<String, String>();
            responseMap = parseResponse(response.toString());
            return((String)responseMap.get("puuid"));
        }
        catch (Exception e){
            System.out.println("Error: Couldn't fetch account info" + e);
        }
        return("error");
    }

    public Collection getMatchHistory (String puuidIngress) {
        ArrayList<String> output = new ArrayList<String>();
        try{
            URL matchHistoryURL = new URL("https://americas.api.riotgames.com/tft/match/v1/matches/by-puuid/" + puuidIngress + "/ids?start=0&count=20&api_key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) matchHistoryURL.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("Response Code : " + (int)conn.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            String removedBrackets = response.toString().substring(1, response.toString().length()-1);
            for(String element : removedBrackets.split(",")){
                output.add(element.substring(1, element.length()-1));
            }
        }
        catch(Exception e){
            System.out.println("Error: Getting match history of puuid: " + puuidIngress);
        }
        String[] arr = new String[1];
        return (output);
    }

    private HashMap<String,String>parseResponse(String httpResponse){
        httpResponse = httpResponse.substring(1, httpResponse.length()-1);
        HashMap<String, String> output = new HashMap<String, String>();
        String[] keyValuePairs = httpResponse.split(",");
        try{
            for(String element: keyValuePairs){
                if(element.indexOf("\":\"") > 0){
                    output.put(element.substring(1, element.indexOf("\":\"")), element.substring(element.indexOf("\":\"")+3, element.length()-1));
                }
                else if(element.indexOf("\":") > 0)
                    output.put(element.substring(1, element.indexOf("\":")), element.substring(element.indexOf("\":")+2, element.length()));
            }
        }
        catch(Exception e) {
            System.out.println("Error: Parsing Response " + e);
        }
        for(String  key : output.keySet()) {
            System.out.println(key + " : " + output.get(key));
        }
        return output;
    }
}
