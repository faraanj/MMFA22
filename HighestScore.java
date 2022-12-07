package boggle.achievements;


import java.util.ArrayList;

public class HighestScore implements Achievements{

    @Override
    public String getDescription(ArrayList<String> value) {
        String highestScore = "0";
        for (int i = 0; i < value.size(); i++){
            String curr_score = value.get(i);
            if (Integer.parseInt(curr_score) > Integer.parseInt(highestScore)){
                highestScore = curr_score;
            }
        }
        return "Your highest score is: " + highestScore.toString();
    }
}