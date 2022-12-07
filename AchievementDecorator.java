package boggle.decorator;

import boggle.achievements.Achievements;

import java.util.ArrayList;

/**
 * Class AchievementDecorator
 */
public class AchievementDecorator implements Achievements{
    private final Achievements achievement;

    /**
     * AchievmentDecorator class constructor
     */
    public AchievementDecorator(Achievements achievement) {
        this.achievement = achievement;
    }

    @Override
    public String getDescription(ArrayList<String> value){
        String curr_description = this.achievement.getDescription(value);
//        for (int i = 0; i < value.size(); i++){
//            String curr_word = curr_description[i];
//        }
        return curr_description + value;
    }
}