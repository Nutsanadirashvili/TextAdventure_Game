package study.coco.gameobjects;

public class ItemDefine {

    private String name;
    private String description;
    private int value;

    //constructor 1
    public ItemDefine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //constructor 2
    public ItemDefine(String name, String description, int value){
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        this.name = aName;
    }

    public String getDescription() {

        return description;
    }

}

