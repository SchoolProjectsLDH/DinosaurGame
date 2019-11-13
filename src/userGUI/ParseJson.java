
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


public class ParseJson {

    
    JSONArray highScoresList;
    public boolean isHighScore = false;
    int ranking;

    @SuppressWarnings("unchecked")
    public ParseJson(String path, String playerName, int score) { //These are passed from the lose class that occurs once the user dies
        StringBuilder contentBuilder = new StringBuilder(); //Stringbuilder class allows for a mutable string, i.e. one can append characters

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {//stream creates an object for every line in the file
            stream.forEach(s -> contentBuilder.append(s).append("\n"));//Lambda appends each stream object to the StringBuilder

            highScoresList = new JSONArray(contentBuilder.toString());//A JSON array is created from the .JSON file 

            highScoresList = SortFile(highScoresList);//Sorts list so that the lowest score in the file is the last item in array
            if (score > highScoresList.getJSONObject(highScoresList.length() - 1).getInt("Score") || highScoresList.length() < 10) {
                //^^^ Add the current highscore only if it is greater than the smallest score in the array or the length of the array is less than 10
                JSONObject newPlayer = new JSONObject("{   \"Score\":" + score + ", \"Name\":" + playerName + "}");
                isHighScore = true;
                highScoresList.put(newPlayer);
                highScoresList.remove(highScoresList.length()-2);//Maintains a constant 10 items in array
                highScoresList = SortFile(highScoresList);//Resorts file with new item added
                this.ranking = searchForPlace(highScoresList, score, playerName);//Finds the ranking of the current score relative to all the others
            }

            writeToFile(highScoresList, path);//Save array to .JSON array

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int searchForPlace(JSONArray arr, int score, String User) {
        for (int i = 0; i < arr.length(); i++) {
            if (highScoresList.getJSONObject(i).getInt("Score") == score && highScoresList.getJSONObject(i).get("Name").toString().equals(User)) {
                //This makes sure the score is not someone else's and rather the actual player's (since the same score can be very common)
                return i + 1;
                
            }
        }
        return 0;
    }
    
    public static JSONArray returnArray(String path){//Static since it is simply a conversion function and aids in convience, not used in any instance of a class
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
        Collections.sort(jsonValues, new Comparator<JSONObject>() {//Compares each item in the JSON Array
            //You can change "Score" with "User" if you want to sort by ID

            @Override
            public int compare(JSONObject a, JSONObject b) {

                //valA and valB could be any simple type, such as number, string, whatever
                int valA = (int) a.get("Score");
                int valB = (int) b.get("Score");

                if (valA < valB) {
                    return 1;//1, -1, and 0 are used by the comparator to decide whether A or B is larger
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

    public void writeToFile(JSONArray highscores, String path) {//Finalize the highscores by saving them to an actual location
        try (FileWriter file = new FileWriter(path)) {

            file.write(highscores.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
