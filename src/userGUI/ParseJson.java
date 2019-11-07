/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userGUI;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

import java.nio.file.*;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Emperor Master Chen
 */
public class ParseJson {

    /**
     *
     * @author Emperor Master Chen
     */
    JSONArray highScoresList;
    public boolean isHighScore = false;
    int ranking;

    @SuppressWarnings("unchecked")
    public ParseJson(String path, String playerName, int score) {
        //JSON parser object to parse read file
        //JSONParser jsonParser = new JSONParser();
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));

            highScoresList = new JSONArray(contentBuilder.toString());

            highScoresList = SortFile(highScoresList);
            if (score > highScoresList.getJSONObject(highScoresList.length() - 1).getInt("Score") || highScoresList.length() < 10) {
                JSONObject newPlayer = new JSONObject("{   \"Score\":" + score + ", \"Name\":" + playerName + "}");
                isHighScore = true;
                highScoresList.put(newPlayer);
                highScoresList.remove(highScoresList.length()-2);
                highScoresList = SortFile(highScoresList);
                this.ranking = searchForPlace(highScoresList, score, playerName);
            }

            writeToFile(highScoresList, path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int searchForPlace(JSONArray arr, int score, String User) {
        for (int i = 0; i < arr.length(); i++) {
            if (highScoresList.getJSONObject(i).getInt("Score") == score && highScoresList.getJSONObject(i).get("Name").toString().equals(User)) {
                return i + 1;
                
            }
        }
        return 0;
    }
    
    public static JSONArray returnArray(String path){
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));

            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray(contentBuilder.toString());
    }

    public JSONArray SortFile(JSONArray highscores) {
        ArrayList<JSONObject> jsonValues = new ArrayList<>();
        for (int i = 0; i < highscores.length(); i++) {
            jsonValues.add(highscores.getJSONObject(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "Name";

            @Override
            public int compare(JSONObject a, JSONObject b) {

                //valA and valB could be any simple type, such as number, string, whatever
                int valA = (int) a.get("Score");
                int valB = (int) b.get("Score");

                if (valA < valB) {
                    return 1;
                }
                if (valA > valB) {
                    return -1;
                }
                return 0;
            }
        }
        );
        JSONArray sortedJsonArray = new JSONArray();

        for (int i = 0; i < jsonValues.size(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray;

    }

    public void writeToFile(JSONArray highscores, String path) {
        try (FileWriter file = new FileWriter(path)) {

            file.write(highscores.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
