package objects;

import objects.checks.CheckType;

public enum CharacterClass {

    BARBARIAN,
    BARD,
    CLERIC,
    DRUID,
    FIGHTER,
    MONK,
    SORCERER,
    ROGUE;

    boolean getProficiency(String type){
        return switch (this) {
            case BARBARIAN, FIGHTER -> type.equals("strength") || type.equals("constitution");
            case BARD -> type.equals("dexterity") || type.equals("charisma");
            case CLERIC -> type.equals("wisdom") || type.equals("charisma");
            case DRUID -> type.equals("wisdom") || type.equals("intelligence");
            case MONK -> type.equals("strength") || type.equals("dexterity");
            case SORCERER -> type.equals("constitution") || type.equals("charisma");
            case ROGUE -> type.equals("dexterity") || type.equals("intelligence");
        };

    }

    boolean checkTypeIsProficient(CheckType type) {
        if (type == CheckType.Spell) return true;
        return switch (this) {
            case BARBARIAN, FIGHTER -> true;
            case BARD, CLERIC, DRUID, MONK, ROGUE -> type == CheckType.SimpleMeleeWeapons || type == CheckType.SimpleRangedWeapons;
            case SORCERER -> type == CheckType.SimpleRangedWeapons;
        };
    }

}
