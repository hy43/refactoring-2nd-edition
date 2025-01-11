package refactoring.app.domain;

import java.util.HashMap;
import java.util.Map;

public class Plays {
    private Map<String, Play> playMap;

    public Map<String, Play> getPlayMap() {
        return playMap;
    }

    public void setPlayMap(Map<String, Play> playMap) {
        this.playMap = playMap;
    }

    public Play findByPlayID(String playID) {
        return playMap.get(playID);
    }
}
