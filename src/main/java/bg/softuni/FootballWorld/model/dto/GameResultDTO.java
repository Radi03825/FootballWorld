package bg.softuni.FootballWorld.model.dto;

public class GameResultDTO {

    private Long myPlayer;

    private String myPlayerFullName;

    private String opponentFullName;

    private Long winner;

    private int myPlayerPoints;

    private int opponentPoints;

    private String myPlayerImageUrl;

    private String opponentImageUrl;

    public Long getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Long myPlayer) {
        this.myPlayer = myPlayer;
    }

    public String getMyPlayerFullName() {
        return myPlayerFullName;
    }

    public void setMyPlayerFullName(String myPlayerFullName) {
        this.myPlayerFullName = myPlayerFullName;
    }

    public String getOpponentFullName() {
        return opponentFullName;
    }

    public void setOpponentFullName(String opponentFullName) {
        this.opponentFullName = opponentFullName;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public int getMyPlayerPoints() {
        return myPlayerPoints;
    }

    public void setMyPlayerPoints(int myPlayerPoints) {
        this.myPlayerPoints = myPlayerPoints;
    }

    public int getOpponentPoints() {
        return opponentPoints;
    }

    public void setOpponentPoints(int opponentPoints) {
        this.opponentPoints = opponentPoints;
    }

    public String getMyPlayerImageUrl() {
        return myPlayerImageUrl;
    }

    public void setMyPlayerImageUrl(String myPlayerImageUrl) {
        this.myPlayerImageUrl = myPlayerImageUrl;
    }

    public String getOpponentImageUrl() {
        return opponentImageUrl;
    }

    public void setOpponentImageUrl(String opponentImageUrl) {
        this.opponentImageUrl = opponentImageUrl;
    }
}
