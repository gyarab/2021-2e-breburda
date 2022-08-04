import objects.*;
import objects.Character;
import objects.checks.AttackCheck;
import objects.checks.CheckType;
import objects.checks.DamageType;

import java.util.ArrayList;
import java.util.Objects;



public class DND {

    public static String reset = "\u001B[0m";
    public static String green = "\u001B[32m";

    public static void main(String[] args) throws Exception {
        System.out.println(green);
        System.out.println("Welcome to this DnD Support System");
        System.out.println("If at any point You need any help type: \"info\" ");
        System.out.println(reset);

        Character ch = load();
        Enemy e = parseEnemy();
        ch.checks = parseCheck();

        System.out.println("Insert check name: ");
        ArrayList<Attack> attacks = e.suggestAttackType(ch);
        e.printAttacks(attacks);
    }

    public static ArrayList<AttackCheck> parseCheck() throws Exception {
        MyScanner sc = new MyScanner();
        ArrayList<AttackCheck> checks = new ArrayList<>();

        while (true) {
            String name = sc.next("Add weapon: (When you want to end adding checks just type end after name: )", green + "Enter a name of your weapon for example \"Short Sword\"" + reset);

            if (name.equals("end")) {
                return checks;
            }
            String line = sc.nextLine("Rolls: ", green + "Enter number of rolls and dice to roll for damage with this weapon such as: \"x dx\" : first x = number of rolls; second x = dice to throw" + reset);
            String[] rollsAr = line.split(", ");

            ArrayList<Roll> rolls = new ArrayList<>();
            for (String s : rollsAr) rolls.add(getRoll(s));

            DamageType dmgType = damageType(sc.next(reset + "Damage type: ", green + "Enter a damage type of your weapon: " + java.util.Arrays.asList(DamageType.values())) + reset);

            String x = sc.nextLine("Check type: ", green + "Enter the check type of your attack: " + java.util.Arrays.asList(CheckType.values()) + reset);
            CheckType checkType = getCheckType(x);

            String characterAttr = getCharacterAttribute(sc.next("Character attribute: ", green + "Enter a character attribute your attack needs: " + java.util.Arrays.asList(CharacterAttribute.values())) + reset);


            checks.add(
                    new AttackCheck(
                            name,
                            checkType,
                            characterAttr,
                            dmgType,
                            rolls
                    )
            );
        }
    }

    public static Roll getRoll(String roll) throws Exception {
        DieType type = switch (roll.split(" ")[1].toUpperCase()) {
            case "D4" -> DieType.D4;
            case "D6" -> DieType.D6;
            case "D8" -> DieType.D8;
            case "D10" -> DieType.D10;
            case "D12" -> DieType.D12;
            case "D20" -> DieType.D20;
            default -> throw new Exception("Invalid value; for help type: \"info\"");
        };

        return new Roll(
                type,
                Integer.parseInt(roll.split(" ")[0])
        );
    }

    public static String getCharacterAttribute(String attr) throws Exception {
        return switch (attr.toUpperCase()) {
            case "STRENGTH" -> "strength";
            case "DEXTERITY" -> "dexterity";
            case "INTELLIGENCE" -> "intelligence";
            case "WISDOM" -> "wisdom";
            case "CHARISMA" -> "charisma";
            default -> throw new Exception("Invalid character attribute; for help type: \"info\"");
        };
    }

    public static CheckType getCheckType(String ch) throws Exception {
        return switch (ch.toUpperCase()) {
            case "SIMPLE MELEE WEAPON" -> CheckType.SimpleMeleeWeapons;
            case "SIMPLE RANGED WEAPON" -> CheckType.SimpleRangedWeapons;
            case "MARTIAL MELEE WEAPON" -> CheckType.MartialMeleeWeapons;
            case "MARTIAL RANGED WEAPON" -> CheckType.MartialRangedWeapons;
            case "SPELL" -> CheckType.Spell;
//            case "ARMOR" -> CheckType.Armor;
            default -> throw new Exception("Invalid value; for help type: \"info\"");
        };
    }

    public static Character load() throws Exception {
        MyScanner sc = new MyScanner();

        String[] names = new String[]{
                "strength",
                "dexterity",
                "constitution",
                "intelligence",
                "wisdom",
                "charisma",
        };
        int[] stats = new int[6];
        int idx = 0;

        while (idx < 6) {
            String name = names[idx];
            stats[idx] = sc.nextInt(name + ": ", green + "Enter a value 1 - 20" + reset);
            idx++;
        }

        String name = sc.next("Name: ", green + "Enter name of your character" + reset);
        String className = sc.next("Class: ", green + "Enter class of your character: " + java.util.Arrays.asList(CharacterClass.values()) + reset);
        CharacterClass characterClass = switch (className.toUpperCase()) {
            case "BARBARIAN" -> CharacterClass.BARBARIAN;
            case "BARD" -> CharacterClass.BARD;
            case "CLERIC" -> CharacterClass.CLERIC;
            case "DRUID" -> CharacterClass.DRUID;
            case "FIGHTER" -> CharacterClass.FIGHTER;
            case "MONK" -> CharacterClass.MONK;
            case "SORCERER" -> CharacterClass.SORCERER;
            case "ROGUE" -> CharacterClass.ROGUE;
            default -> throw new Exception("Invalid character class; for help type: \"info\"");
        };

        return new Character(
                name,
                characterClass,
                stats[0],
                stats[1],
                stats[2],
                stats[3],
                stats[4],
                stats[5]
        );
    }

    public static DamageType[] parseSet(MyScanner sc, String title, String info) {
        try {
            String[] weaknessesAr = sc.nextLine(title, info).split(", ");

            if (Objects.equals(weaknessesAr[0], "none")) {
                return new DamageType[0];
            }

            DamageType[] attrSet = new DamageType[weaknessesAr.length];
            for (int i = 0; i < attrSet.length; i++) attrSet[i] = damageType(weaknessesAr[i]);
            return attrSet;
        } catch (Exception ex) {
            System.out.println(green + "Not a walid option, try again; for help type: \"info\"" + reset);
            return parseSet(sc, title, info);
        }
    }

    public static Enemy parseEnemy() {
        MyScanner sc = new MyScanner();
        String name = sc.next("Enemy name: ", green + "Enter the name of your enemy" + reset);

        DamageType[] weaknesses = parseSet(sc, "Weaknesses: (split single damage types by comma and gap)", green + "Enter damage type you know the enemy has weakness to: " + java.util.Arrays.asList(DamageType.values()) + reset);
        DamageType[] resistances = parseSet(sc, "Resistances: (split single damage types by comma and gap)", green + "Enter damage type you know the enemy has resistance to: " + java.util.Arrays.asList(DamageType.values()) + reset);
        DamageType[] immunities = parseSet(sc, "Immunities: (split single damage types by comma and gap)", green + "Enter damage type you know the enemy is immune to: " + java.util.Arrays.asList(DamageType.values()) + reset);
        return new Enemy(
                name,
                weaknesses,
                resistances,
                immunities
        );
    }

    public static DamageType damageType(String d) throws Exception {
        return switch (d.toUpperCase()) {
            case "ACID" -> DamageType.ACID;
            case "BLUDGEONING" -> DamageType.BLUDGEONING;
            case "COLD" -> DamageType.COLD;
            case "FIRE" -> DamageType.FIRE;
            case "FORCE" -> DamageType.FORCE;
            case "LIGHTNING" -> DamageType.LIGHTNING;
            case "NECROTIC" -> DamageType.NECROTIC;
            case "PIERCING" -> DamageType.PIERCING;
            case "POISON" -> DamageType.POISON;
            case "PSYCHIC" -> DamageType.PSYCHIC;
            case "RADIANT" -> DamageType.RADIANT;
            case "SLASHING" -> DamageType.SLASHING;
            case "THUNDER" -> DamageType.THUNDER;
            default -> {
                throw new Exception("Error");
            }
        };
    }

}
