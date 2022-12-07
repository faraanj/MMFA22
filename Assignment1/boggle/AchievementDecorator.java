package boggle;

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

    /**
     * @param value - The value of the achievement
     * @return A string that contains the achievement description and value
     */
    @Override
    public String getDescription(ArrayList<String> value){
        String curr_description = this.achievement.getDescription(value);
        return curr_description + value;
    }
}