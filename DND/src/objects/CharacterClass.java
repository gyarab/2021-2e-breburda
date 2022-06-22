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

    boolean getProficiency(String type) {
        switch (this) {
            case BARBARIAN:
            case FIGHTER:
                return type.equals("strength") || type.equals("constitution");
            case BARD:
                return type.equals("dexterity") || type.equals("charisma");
            case CLERIC:
                return type.equals("wisdom") || type.equals("charisma");
            case DRUID:
                return type.equals("wisdom") || type.equals("intelligence");
            case MONK:
                return type.equals("strength") || type.equals("dexterity");
            case SORCERER:
                return type.equals("constitution") || type.equals("charisma");
            case ROGUE:
                return type.equals("dexterity") || type.equals("intelligence");
        }

        return false;
    }

    boolean checkTypeIsProficient(CheckType type) {
        if (type == CheckType.Spell) return true;
        switch (this) {
            case BARBARIAN:
            case FIGHTER:
                return true;
            case BARD:
            case CLERIC:
            case DRUID:
            case MONK:
            case ROGUE:
                return type == CheckType.SimpleMeleeWeapons || type == CheckType.SimpleRangedWeapons;
            case SORCERER:
                return type == CheckType.SimpleRangedWeapons;
        }
        return false;
    }

}
