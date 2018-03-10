package checksmart;

public class Move {

    private final long id;
    private final String content;
    private final String player;

    public Move(long id, String content, String player) {
        this.id = id;
        this.content = content;
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPlayer() {
        return player;
    }

    public boolean isvalid() {
        // TODO
        return true;
    }
}
