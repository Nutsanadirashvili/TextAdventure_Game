package study.coco.gameobjects;

public class ItemHolder extends ItemDefine {

    private ItemList items = new ItemList();

    //Constructor
    public ItemHolder(String name, String description, ItemList list) {
        super(name, description);
        items = list;
    }

    public ItemList getItems() {
        return items;
    }



}

