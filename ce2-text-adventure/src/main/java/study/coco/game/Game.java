package study.coco.game;
import java.io.IOException;
import java.util.*;
import static org.fusesource.jansi.Ansi.*;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import study.coco.gameobjects.Player;
import study.coco.gameobjects.Location;
import study.coco.gameobjects.ItemDefine;
import study.coco.gameobjects.ItemList;
import study.coco.gameobjects.Items;
import study.coco.directions.Direction;

public class Game {

    private static Terminal terminal;
    private static LineReader reader;
    private ArrayList<Location> map;
    private Player player;
    private int isReady = -1; // count point of the item value

    List<String> commands = new ArrayList<>(Arrays.asList(
            "take", "drop", "look", "use", "l", "i", "inventory",
            "n", "s", "w", "e", "check"));
    List<String> allItems = new ArrayList<>(Arrays.asList("responsibility", "comfort",
            "courage", "fear", "voice", "notmyproblem", "solidarity"));

    public Game() throws IOException {

        //User input
        terminal = TerminalBuilder.builder().jansi(true).system(true).build();
        reader = LineReaderBuilder.builder().terminal(terminal).history(new DefaultHistory()).build();

        //create List of Locations
        this.map = new ArrayList<>();


        //Create items
        Items resp   = new Items("responsibility", "the state or fact of having a duty to deal with something or of having control over someone.", 1);
        Items comf   = new Items("comfort", "a state of physical ease and freedom from pain or constraint.", -1);
        Items solid  = new Items("solidarity", "unity or agreement of feeling or action, especially among individuals with a common interest;", 1);
        Items cour   = new Items("courage", "the ability to do something that frightens one", 1);
        Items fear   = new Items("fear", "an unpleasant emotion caused by the threat of danger, pain, or harm,", -1);
        Items voice  = new Items("voice", "a particular opinion or attitude expressed.", 1);
        Items nmp    = new Items("notmyproblem", "usual position for modern human being.", -1);

        // Create lists for Locations and player
        ItemList redlandList    = new ItemList();
        ItemList whitelandList  = new ItemList();
        ItemList bluelandList   = new ItemList();
        ItemList yellowlandList = new ItemList();
        ItemList playerlist     = new ItemList();

        //add items to the lists
        redlandList.add(comf);
        whitelandList.add(resp);
        whitelandList.add(fear);
        bluelandList.add(cour);
        bluelandList.add(voice);
        yellowlandList.add(solid);
        playerlist.add(nmp);

        //create rooms and add to the list
        map.add(new Location(String.valueOf(ansi().fgRgb(255,38,38).a("Redland").reset()), "A dark place with chaos and fire.", Direction.NOEXIT, 2, Direction.NOEXIT, 1, redlandList));
        map.add(new Location(String.valueOf(ansi().fgRgb(195,195,195).a("Greyland").reset()), "A lonely place without noise.", Direction.NOEXIT, Direction.NOEXIT, 0, Direction.NOEXIT, whitelandList));
        map.add(new Location(String.valueOf(ansi().fgRgb(255,188,0).a("Yellowland").reset()), "A joy place with colors and music.", 0, Direction.NOEXIT, Direction.NOEXIT, 3, yellowlandList));
        map.add(new Location(String.valueOf(ansi().fgRgb(30,44,236).a("Bluland").reset()), "A calm place with peace and blue sky.", Direction.NOEXIT, Direction.NOEXIT, 2, Direction.NOEXIT, bluelandList));


        //Create Player
        welcome();
        player = new Player("", " ", playerlist, map.get(0));
        String playername;
        System.out.println("Hi, let me know your name at first.");
        playername = reader.readLine("My name is: ");

        if(playername.isEmpty()){                                     //isBlank does not work :(
            player.setName(String.valueOf(ansi().fgRgb(0,255,199).a("'I Don't Want to write my Name'").reset()));
        }else if(playername.equals(" ")){
            player.setName(String.valueOf(ansi().fgRgb(0,255,119).a("'Blank Space'").reset()));
        }else {
            player.setName(String.valueOf(ansi().fgRgb(0,255,119).a(playername).reset()));
        }

        textline();
    }


    public Player getPlayer() {
        return player;
    }


    // take, drop, use commands

    private void moveItem(ItemDefine t, ItemList sourceList, ItemList destinationList) {
        sourceList.remove(t);
        destinationList.add(t);
    }

    public String takeItem(String itemName) {
        String returnText = "";
        ItemDefine i = player.getRoom().getItems().searchItem(itemName);
        if (itemName.equals("")) {
            itemName = "nameless item";
        }
        if (i == null) {
            returnText = "There is no " + itemName + " here!";
        } else {
            moveItem(i, player.getRoom().getItems(), player.getItems());
            returnText = ansi().fgRgb(0, 255, 119).a(itemName).reset() + " taken!";
            isReady += i.getValue();
        }
        return returnText;
    }

    public String useItem(String itemName) {
        String returnText = "";
        ItemDefine i = player.getRoom().getItems().searchItem(itemName);
        if (isReady < 4 && itemName.equals("voice")) {
            returnText = "Not useable right now. First you need to find another essentials.";
        } else {
            returnText = "not usable right now.";
        }
        if(isReady == 4){
            if(itemName.equals("voice")){
                returnText = ansi().fgRgb(0, 255, 119).a(itemName).reset() + " used.";
                isReady ++;
                printText("\n\n\n#######################################################################\n"+
                        ansi().fgRgb(255, 155, 0).bold().a("The only way to find your voice, is to use it!\n").reset() +
                                "#######################################################################\n\n"+
                        ansi().bold().a("Congratulation! You did it! Your voice has POWER!\n\n").reset() +
                        ansi().fgRgb(0, 87, 183).a(".O°o. .o°O________________________________O°o. .o°O.\n" +
                        ".°o.O.o° ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯.°o.O.o°\n" +
                        "░░░░░╔══╦╗░░░░╔╗░░░░░░╔╗╔╗░░░░░░░░\n" +
                        "░░░░░╚╗╔╣╚╦═╦═╣║╔╗░░░░║║║╠═╦╦╗░░░░\n").reset() +
                        ansi().fgRgb(255, 215, 0).a("░░░░░░║║║║╠╝║║║╠╝║░░░░║╚╝║║║║║░░░░\n" +
                        "░░░░░░║║║║║║║║║╔╗╣░░░░╚╗╔╣║║║║░░░░\n" +
                        "░░░░░░╚╝╚╩╩═╩╩╩╝╚╝░░░░░╚╝╚═╩═╝░░░░\n" +
                        ".O°o. .o°O________________________________O°o. .o°O.\n" +
                        ".°o.O.o° ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯.°o.O.o°..\n\n").reset());
                System.exit(0);
            }else{
                returnText = "try another item.";
            }
        }
        return returnText;
    }

    public String dropItem(String itemName) {
        String returnText = "";
        ItemDefine i = player.getItems().searchItem(itemName);
        if (itemName.equals("")) {
            returnText = "You'll have to tell me which object you want to drop!";
        } else if (i == null) {
            returnText = "You haven't got one of those!";
        } else {
            moveItem(i, player.getItems(), player.getRoom().getItems());
            returnText = ansi().fgRgb(0, 255, 119).a(itemName).reset() + " dropped!";
            if(i.getValue() == -1){
                isReady ++;
            }else{
                isReady --;
            }

        }
        return returnText;
    }
    public void checkSituation(){
        if(isReady < 4){
            System.out.println("Sorry not enough. Please try your best");
        }else if(isReady == 4){
            System.out.println("Yeah, you are in the right way. Now you must use one of your item to win this bad game.");
        }
    }


    void giveNewLocation(Player player, Location currentLocation) {
        player.setRoom(currentLocation);
    }


    int chooseDirection(Player player, Direction direction) {
        Location l = player.getRoom();
        int go;

        switch (direction) {
            case NORTH:
                go = l.getN();
                break;
            case SOUTH:
                go = l.getS();
                break;
            case EAST:
                go = l.getE();
                break;
            case WEST:
                go = l.getW();
                break;
            default:
                go = Direction.NOEXIT;
                break;
        }
        if (go != Direction.NOEXIT) {
            giveNewLocation(player, map.get(go));
        }
        return go;
    }

    public int movePlayer(Direction direction) {

        return chooseDirection(player, direction);
    }

    private void goNorth() {
        showLocationDescription(movePlayer(Direction.NORTH));
    }

    private void goSouth() {
        showLocationDescription(movePlayer(Direction.SOUTH));
    }

    private void goWest() {
        showLocationDescription(movePlayer(Direction.WEST));
    }

    private void goEast() {
        showLocationDescription(movePlayer(Direction.EAST));
    }

    private void look() {
        printText("You are in the " + getPlayer().getRoom().describeLocation());
    }

    private void printText(String s) {
        System.out.println(s);
    }

    private void showLocationDescription(int locationNum) {
        String returnText;
        if (locationNum == Direction.NOEXIT) {
            returnText = String.valueOf(ansi().fgRgb(255,33,33).a("No Exit!").reset());
        } else {
            Location r = getPlayer().getRoom();
            returnText = "You are in the " + r.describeLocation();
        }
        printText(returnText);
        System.out.println("What would you like to do?");
    }

    private void showInventory() {
        printText("You have\n" + getPlayer().getItems().describeItem());
    }

    public String oneWordCommand(List<String> wordlist) {
        String action;
        String msg = "";
        action = wordlist.get(0);
        if (!commands.contains(action)) {
            msg = action + " is not a known action! ";
        } else {
            switch (action) {
                case "n":
                    goNorth();
                    break;
                case "s":
                    goSouth();
                    break;
                case "w":
                    goWest();
                    break;
                case "e":
                    goEast();
                    break;
                case "l":
                case "look":
                    look();
                    break;
                case "inventory":
                case "i":
                    showInventory();
                    break;
                case "check":
                    checkSituation();
                    break;
                default:
                    msg = action + " (Unknown command)";
                    break;
            }
        }
        return msg;
    }

    public String twoWordCommand(List<String> wordlist) {
        String action;
        String object;
        String returnText = "";
        boolean error = false;
        action = wordlist.get(0);
        object = wordlist.get(1);
        if (!commands.contains(action)) {
            returnText = action + " is not a known action! ";
            error = true;
        }
        if (!allItems.contains(object)) {
            returnText += (object + " is not a known object!");
            error = true;
        }
        if (!error) {
            switch (action) {
                case "take":
                    returnText = takeItem(object);
                    break;
                case "drop":
                    returnText = dropItem(object);
                    break;
                case "use":
                    returnText = useItem(object);
                    break;
                default:
                    returnText += " (Unknown command)";
                    break;
            }
        }
        return returnText;
    }

    public String returnRightCommand(List<String> wordlist) {
        String msg;
        if (wordlist.size() == 1) {
            msg = oneWordCommand(wordlist);
        } else if (wordlist.size() == 2) {
            msg = twoWordCommand(wordlist);
        } else {
            msg = "Wrong commands, try again.";
        }
        return msg;
    }

    public List<String> wordList(String input) {
        String delimiters = " \t,.:;?!\"'";
        List<String> userInputList = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input, delimiters);
        String token;

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            userInputList.add(token);
        }
        return userInputList;
    }

    public void intro() {
        String s;
        s = "Hello " + player.getName() + ". Welcome to our world. Thank you for your visit, but bad timing. " +
                "\nUnfortunatelly, the world is going crazy right now. We have War, really bad war." +
                "\n\nBut you know what? you can help us. " +
                "\nGo and find essentials. Try your best, but don't forget. Not everything is essential. Good Luck!\n" +
                "------------------------------------------------------------------------------------------------------\n"+
                ansi().fgRgb(0, 255, 119).a("Here are all commands, that you need:\n"+
                "n               | Go North\n" +
                "s               | Go South\n" +
                "e               | Go East\n" +
                "w               | Go West\n" +
                "i               | Show Inventory\n" +
                "take <item>     | Take current item\n" +
                "drop <item>     | Drop current item\n" +
                "use <item>      | Use current item\n" +
                "look            | show the current locations\n"+
                "check           | check your condition, get some hints\n").reset() +
                "------------------------------------------------------------------------------------------------------\n\n" +
                "Are you ready? Please enter 'ok' if you are ready to start Adventure. Otherwise - 'q' to quit the game.\n";
        printText(s);
        String start=reader.readLine();
        if(start.equalsIgnoreCase("ok")){
            showLocationDescription(0);
        }else if(start.equalsIgnoreCase("q")){
            System.out.println("Thanks for your try.");
            System.exit(0);
        }

    }

    public void textline(){
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    public void welcome(){
        System.out.println(ansi().fgRgb(15,209,67).a("\n\n╔╗─╔╦═══╦╗──╔╗──╔═══╗╔╗\n"+
                "║║─║║╔══╣║──║║──║╔═╗║║║\n"+
                "║╚═╝║╚══╣║──║║──║║─║║║║\n"+
                "║╔═╗║╔══╣║─╔╣║─╔╣║─║║╚╝\n"+
                "║║─║║╚══╣╚═╝║╚═╝║╚═╝║╔╗\n"+
                "╚╝─╚╩═══╩═══╩═══╩═══╝╚╝\n").reset() +
                "\n#####################################################" +
                "\n You are now in text based adventure game" + ansi().fgRgb(0, 133, 222).bold().a(" - 'Reality'.").reset() +
                "\n#####################################################");
    }

    public String run(String inputstr) {
            List<String> wordlist;
            String s = "Thank you for your try.";
            String lowstr = inputstr.trim().toLowerCase();
            if (!lowstr.equals("q")) {
                if (lowstr.equals("")) {
                    s = "You must enter a command";
                } else {
                    wordlist = wordList(lowstr);
                    s = returnRightCommand(wordlist);
                }
            }
            return s;

    }

}
