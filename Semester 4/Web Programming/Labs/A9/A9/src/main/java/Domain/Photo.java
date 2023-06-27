package Domain;

public class Photo {
    private int id;
    private String url;
    private int userID;
    private int votes;

    public Photo() {
    }

    public Photo(int id, String url, int userID, int votes) {
        this.id = id;
        this.url = url;
        this.userID = userID;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getuserID() {
        return userID;
    }

    public void setuserID(int userID) {
        this.userID = userID;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", userID=" + userID +
                ", votes=" + votes +
                '}';
    }
}
