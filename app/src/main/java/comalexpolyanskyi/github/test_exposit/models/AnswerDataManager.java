package comalexpolyanskyi.github.test_exposit.models;

/**
 * Created by Алексей on 17.06.2016.
 */
public class AnswerDataManager {
    private String refreshingURL = null;
    private int age = 0;

    public AnswerDataManager(String url, int age) {
        this.refreshingURL = url;
        this.age = age;
    }

    public int getMaxAge() {
        return age;
    }

    public String getRefreshingURL() {
        return refreshingURL;
    }
}
