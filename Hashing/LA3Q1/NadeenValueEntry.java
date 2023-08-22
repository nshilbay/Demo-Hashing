package LA3Q1;

public class NadeenValueEntry {

    private Integer key;

    //no argument constructor
    public NadeenValueEntry (){
        key = -1;
    }

    //constructor with argument
    public NadeenValueEntry (Integer key){
        this.key = key;
    }

    //setter method
    public void setKey(Integer key){
        this.key = key;
    }

    //getter method
    public Integer getKey(){
        return key;
    }
}
