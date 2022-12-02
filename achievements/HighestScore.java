package boggle.achievements;


public class HighestScore implements Achievements{

    @Override
    public String getDescription(String value) {
        return "Your highest score is: ";
    }
}
