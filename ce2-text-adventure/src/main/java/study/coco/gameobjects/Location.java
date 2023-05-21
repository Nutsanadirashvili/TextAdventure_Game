
package study.coco.gameobjects;

import static org.fusesource.jansi.Ansi.ansi;

public class Location extends ItemHolder {

    private final int n;
    private final int s;
    private final int w;
    private final int e;

    public Location(String name, String description, int north, int south, int west, int east,
                    ItemList list) {
        super(name, description, list);
        this.n = north;
        this.s = south;
        this.w = west;
        this.e = east;
    }


    // Get all directions
    public int getN() {
        return n;
    }

    public int getS() {
        return s;
    }

    public int getE() {
        return e;
    }

    public int getW() {
        return w;
    }



    //describe location
    public String describeLocation() {
        return String.format("%s. %s.",
                getName(), getDescription())
                + "\nYou can find here:\n" + getItems().describeItem();
    }


}
