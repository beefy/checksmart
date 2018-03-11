package checksmart;

public class Move {

    private String movefrom;
    private String moveto;

    public Move() {
    }

    public Move(String movefrom, String moveto) {
        this.movefrom = movefrom;
        this.moveto = moveto;
    }

    public String getMoveFrom() {
        return movefrom;
    }

    public String getMoveTo() {
        return moveto;
    }

    public String toString() {
        return "Move from " + movefrom + " to " + moveto;
    }

}
