package boggle;


import java.util.ArrayList;

/**
 * Highest Score Achievement
 */
public class HighestScore implements Achievements{
    /**
     * @param value - An arrayList containing all the user's scores
     * @return The heighest score from <value>
     */
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