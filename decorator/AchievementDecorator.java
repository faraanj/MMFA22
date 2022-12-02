package boggle.decorator;

import boggle.achievements.Achievements;

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
    public String getDescription(String value){
        String curr_description = this.achievement.getDescription("");
        return curr_description + value;
    }
}
