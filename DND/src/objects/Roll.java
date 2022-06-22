package objects;

public class Roll {

    DieType die;
    int count;

    public Roll(DieType die, int count) {
        this.die = die;
        this.count = count;
    }

    public int roll(){
        int counter = 0;
        for (int i = 0; i < count; i++){
            counter += (int) Math.floor(Math.random() * die.die()) + 1;
        }
        return counter;
    }

    public static int RollD20(){
        return (int) Math.floor(Math.random() * DieType.D20.die()) + 1;
    }
}
