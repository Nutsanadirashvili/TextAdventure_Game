package study.coco.gameobjects;

public class Player extends ItemHolder {

    private Location location; // current room

    public Player(String name, String description, ItemList list, Location location) {
        super(name, description, list);
        this.location = location;
    }

    public void setRoom(Location aLocation) {
        this.location = aLocation;
    }

    public Location getRoom() {
        return this.location;
    }
}























//    private String name = "Jeck";
//    private int beerPower;
//    private Rooms currentRoom;
//    private boolean[] inventory;
//    MagicDrink beer = new MagicDrink();
//
//    public Player() {
//
//    }
//
//    public Player(String name, int beerPower, Rooms currentRoom){
//        this.name = name;
//        this.beerPower = beerPower;
//        this.currentRoom = currentRoom;
//
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean[] getInventory() {
//        return inventory;
//    }
//
//    public void setInventory(boolean[] inventory) {
//        this.inventory = inventory;
//    }
//
//    public Rooms getCurrentRoom() {
//        return currentRoom;
//    }
//
//    public void setCurrentRoom(Rooms currentRoom) {
//        this.currentRoom = currentRoom;
//    }
//
//    public int getBeerPower() {
//        return beerPower;
//    }
//
//    public void setBeerPower(int beerPower) {
//        this.beerPower = beerPower;
//    }
//
//    public int checkBeer(){
//
//        if(isKolsch()){
//            beerPower += 25;
//            System.out.println("You Lucky one. Here is cold Kölsch for you. enjoy with it");
//        }else{
//            beerPower = beerPower - 15;
//            System.out.println("Oh I am sorry but you there was Altbier, take care of your power");
//        }
//        return beerPower;
//    }
//
//    public String checkPower() {
//        String displayPower = this.name + ", your current Beer Power is: " + this.beerPower + "/3.";
//        return displayPower;
//    }
