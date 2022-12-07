package boggle;


import java.util.ArrayList;

public class LongestWord implements Achievements {

    @Override
    public String getDescription(ArrayList<String> value) {
        String longestWord = "";
        for (int i = 0; i < value.size(); i++){
            String curr_word = value.get(i);
            if (curr_word.length() > longestWord.length()){
                longestWord = curr_word;
            }
        }
        return "Your longest word found is: " + longestWord;
    }
}