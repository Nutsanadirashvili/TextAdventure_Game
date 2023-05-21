package study.coco.gameobjects;
import java.util.ArrayList;
import static org.fusesource.jansi.Ansi.ansi;


public class ItemList extends ArrayList<ItemDefine> {

    // returns item name and item description
    public String describeItem() {
        String returnText = "";
        if (this.size() == 0) {
            returnText = "nothing.\n";
        } else {
            for (ItemDefine i : this) {
                returnText = returnText + ansi().fgRgb(0, 255, 119).a(i.getName()).reset() + ": " + i.getDescription() + "\n";
            }
        }
        return returnText;
    }


    public ItemDefine searchItem(String name) {
        ItemDefine item = null;
        String itemName = "";
        String aNameLowCase = name.trim().toLowerCase();
        for (ItemDefine t : this) {
            itemName = t.getName().trim().toLowerCase();
            if (itemName.equals(aNameLowCase)) {
                item = t;
            }
        }
        return item;
    }
}
