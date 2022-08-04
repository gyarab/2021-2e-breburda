package objects;

public enum DieType {

    D4,
    D6,
    D8,
    D10,
    D12,
    D20;

    public int die(){
        return switch (this) {
            case D4 -> 4;
            case D6 -> 6;
            case D8 -> 8;
            case D10 -> 10;
            case D12 -> 12;
            case D20 -> 20;
        };
    }


}
