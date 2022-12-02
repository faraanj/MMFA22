package boggle.achievements;


public class LongestWord implements Achievements{

    @Override
    public String getDescription(String value) {
        return "Your longest word found is: ";
    }
}
