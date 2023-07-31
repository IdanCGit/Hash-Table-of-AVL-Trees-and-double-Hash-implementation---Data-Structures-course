/**
 * This is a testing framework. 
 */
public class Tester {

    private static boolean testPassed = true;
    private static int testNum = 0;

    /**
     * This entry function will test all classes created in this assignment.
     * @param args command line arguments
     */
    public static void main(String[] args) {

        //SpellSimple
        testSpellSimple();
        
        //DoubleHashTable
        testDoubleHashTable();

        //Spell
        testSpell();

        //AVLTree
        testAVLTree();

        //HashAVLSpellTable
        testHashAVLSpellTable();

        // Notifying the user that the code have passed all tests.
        if (testPassed) {
            System.out.println("All " + testNum + " tests passed!");
        }
    }

    /**
     * This utility function will count the number of times it was invoked.
     * In addition, if a test fails the function will print the error message.
     * @param exp The actual test condition
     * @param msg An error message, will be printed to the screen in case the test fails.
     */
    private static void test(boolean exp, String msg) {
        testNum++;

        if (!exp) {
            testPassed = false;
            System.out.println("Test " + testNum + " failed: "  + msg);
        }
    }

    /**
     * Checks the SpellSimple class.
     */
    private static void testSpellSimple() {

        SpellSimple  spell = new SpellSimple("Abracadabra", "Avada Kedavra");

        // Test name getter
        test(spell.getName().equals("Abracadabra"),"The getName of the given spell should be 'Abracadabra' got: '" + spell.getName()+ "'");

        // Test words getter
        test(spell.getWords().equals("Avada Kedavra"),"The getWords of the given spell should be 'Avada Kedavra' got: '" + spell.getName()+ "'");
    }


    /**
     * Checks the DoubleHashTable class.
     */
    private static void testDoubleHashTable() {

        DoubleHashTable table = new DoubleHashTable(7);

        // Add some spells to the table
        table.put(new SpellSimple("Abracadabra", "Avada Kedavra"));
        table.put(new SpellSimple("Expecto Patronum", "Im gonna stand here like a unicorn"));
        table.put(new SpellSimple("Wingardium Leviosa", "Get up, stand up"));
        table.put(new SpellSimple("Shazam", "24K Magic in the air"));


        // Get the spells by name
        test(table.getCastWords("Shazam").equals("24K Magic in the air"),"The getCastWords of Shazam should be '24K Magic in the air' got: '" + table.getCastWords("Shazam")+ "'");

        // Get the size of the table
        test(table.getSize() == 4, "The size of the given table should be 4, instead got: '" + table.getSize() + "'");

        // Get the last steps
        test(table.getLastSteps() == 1, "The number of steps taken in the last action should be 1, instead got: '" + table.getSize() + "'");


        table.put(new SpellSimple("Aberto", "Openo locked doorsoo"));
        table.put(new SpellSimple("Accio", "Summon objectio"));

        // Test Overflow
        test(table.put(new SpellSimple("Aguamenti", "Summon waterio")) == true, "Got false, i.e. the table says falsly that it's overflowing, should return true"); // last available place
        test(table.put(new SpellSimple("Alohomora", "Unlocko objectio")) == false, "Got true, i.e. item added to overflowing table, should return false"); // overflow

        // Get the size of a full table, make sure it doesn't say size > capacity
        test(table.getSize() == 7, "The size of the given table should be 7, instead got: '" + table.getSize() + "'");

        // Get non-existing spell
        test(table.getCastWords("Aloha") == null,"The getCastWords of Aloha should return null, got: '" + table.getCastWords("Aloha")+ "'");
    }

    /**
     * Checks the Spell class.
     */
    private static void testSpell() {

        Spell spell = new Spell("Abracadabra", "Fire", 20, "Avada Kedavra");

        test(spell.getName().equals("Abracadabra"),"The getName of the spell should be 'Abracadabra' got: '" + spell.getName() + "'");

        test(spell.getCategory().equals("Fire"),"The getCategory of the spell should be 'Fire' got: '" + spell.getCategory() + "'");

        test(spell.getPowerLevel() == 20,"The getPowerLevel of the spell should be 20 got: '" + spell.getPowerLevel() + "'");

        test(spell.toString().equals("Abracadabra (Fire) - Power Level: 20, to cast say: Avada Kedavra"),"The toString of the spell should be 'Abracadabra (Fire) - Power Level: 20, to cast say: Avada Kedavra' got: '" + spell.toString() + "'");
    }

    /**
     * Checks the AVLTree class.
     */
    private static void testAVLTree() {

        AVLTree tree = new AVLTree(new Spell("Abracadabra", "Fire", 20, "Avada Kedavra"));

        test(tree.getSize() == 1,"The size of the tree should be 1 got: '" + tree.getSize() + "'");
        test(tree.getTreeHeight() == 0,"The height of the tree should be 0 got: '" + tree.getTreeHeight() + "'");

        tree.insert(new Spell("Expecto Patronum", "Fire", 13, "Im gonna stand here like a unicorn"));

        test(tree.getSize() == 2,"The size of the tree should be 2 got: '" + tree.getSize() + "'");
        test(tree.getTreeHeight() == 1,"The height of the tree should be 1 got: '" + tree.getTreeHeight() + "'");

        tree.insert(new Spell("Wingardium Leviosa", "Fire", 11, "Get up, stand up")); // Requires right rotation

        test(tree.getTreeHeight() == 1,"The height of the tree should be 1 got: '" + tree.getTreeHeight() + "'");

        test(tree.getCategory().equals("Fire"),"The category of the tree should be 'Fire' got: '" + tree.getCategory() + "'");

        tree.insert(new Spell("Shazam", "Fire", 25, "24K Magic in the air"));
        tree.insert(new Spell("Aberto", "Fire", 22, "Openo locked doorsoo")); // Requires right rotation and then left rotation

        test(tree.getTreeHeight() == 2,"The height of the tree should be 2 got: '" + tree.getTreeHeight() + "'");

        test(tree.search("Abracadabra",20).toString().equals("Abracadabra (Fire) - Power Level: 20, to cast say: Avada Kedavra"),"The toString of the spell should be 'Abracadabra (Fire) - Power Level: 20, to cast say: Avada Kedavra' got: '" + tree.search("Abracadabra",20) + "'");
        test(tree.search("Abracadabra",21) == null,"The search() of the spell should be null, as it does not exist, got: '" + tree.search("Abracadabra",21) + "'"); // spell exists with different power level
        test(tree.search("Shazam",22) == null,"The search() of the spell should be null, as it does not exist, got: '" + tree.search("Shazam",22) + "'"); // spell with this power level exists, but it is a different one

        test(tree.getTopK(3).toString().equals("[Shazam (Fire) - Power Level: 25, to cast say: 24K Magic in the air, Aberto (Fire) - Power Level: 22, to cast say: Openo locked doorsoo, Abracadabra (Fire) - Power Level: 20, to cast say: Avada Kedavra]"),"getTopK of K = 3 has failed."); 

        tree.insert(new Spell("Accio", "Fire", 9, "Summon objectio")); 
        tree.insert(new Spell("Aguamenti", "Fire", 12, "Summon waterio"));

        test(tree.getTopK(10).toString().equals("[Shazam (Fire) - Power Level: 25, to cast say: 24K Magic in the air, Aberto (Fire) - Power Level: 22, to cast say: Openo locked doorsoo, Abracadabra (Fire) - Power Level: 20, to cast say: Avada Kedavra, Expecto Patronum (Fire) - Power Level: 13, to cast say: Im gonna stand here like a unicorn, Aguamenti (Fire) - Power Level: 12, to cast say: Summon waterio, Wingardium Leviosa (Fire) - Power Level: 11, to cast say: Get up, stand up, Accio (Fire) - Power Level: 9, to cast say: Summon objectio]"),
                                              "getTopK of K = 10 has failed."); 

        tree.insert(new Spell("Anss", "Fire", 26, "Summonio"));
        tree.insert(new Spell("Sirio", "Fire", 27, "To do lo baba")); // left rotation

        test(tree.getTreeHeight() == 3,"The height of the tree should be 3 got: '" + tree.getTreeHeight() + "'");

        tree.insert(new Spell("Rightio", "Fire", 30, "Become right child"));
        tree.insert(new Spell("Leftion", "Fire", 28, "Become left child")); // requires left rotation then right rotation, then 22 also imbalanced, rotate left on it

        test(tree.getTreeHeight() == 3,"The height of the tree should be 3 got: '" + tree.getTreeHeight() + "'");
    }

    /**
     * Checks the HashAVLSpellTable class.
     */
    private static void testHashAVLSpellTable() {
        HashAVLSpellTable table = new HashAVLSpellTable(10);

        // create some spells
        Spell spell1 = new Spell("fireball", "fire", 10, "fireball!");
        Spell spell2 = new Spell("frostbolt", "ice", 7, "freeze please");
        Spell spell3 = new Spell("thunderstorm", "lightning", 9, "I`m going to shock you");
        Spell spell4 = new Spell("poison spray", "poison", 5, "sssss");
        Spell spell5 = new Spell("shockwave", "lightning", 8, "go pikachu!");

        // add the spells to the hash AVL spell table
        table.addSpell(new Spell("lightning bolt", "lightning", 11, "go lightning bolt"));
        table.addSpell(spell1);
        table.addSpell(spell2);
        table.addSpell(spell3);
        table.addSpell(spell4);
        table.addSpell(spell5);

        // number of overall spells
        test(table.getNumberSpells() == 6,"The current number of spells overall should be 6 got: '" + table.getNumberSpells() + "'");

        // add more spells to an existing category
        table.addSpell(new Spell("flamethrower min", "fire", 6, "foo"));
        table.addSpell(new Spell("flamethrower", "fire", 8, "foo better"));
        table.addSpell(new Spell("fireball II", "fire", 12,"fireball!!"));
        table.addSpell(new Spell("flamethrower II", "fire", 15, "foooooooo!"));
        table.addSpell(new Spell("shockwave II", "lightning", 10,"be useful pikachu."));
        table.addSpell(new Spell("frost nova", "ice", 4, "chill dude"));

        // number of overall spells
        test(table.getNumberSpells() == 12,"The current number of spells overall should be 12 got: '" + table.getNumberSpells() + "'");

        // number of spells in fire category
        test(table.getNumberSpells("fire") == 5,"The current number of fire spells should be 5 got: '" + table.getNumberSpells("Fire") + "'");

        // number of spells in a category that doesn't exist
        test(table.getNumberSpells("void") == 0,"The current number of void spells should be 0 got: '" + table.getNumberSpells("void") + "'");

        // top k on category
        test(table.getTopK("fire",3).toString().equals("[flamethrower II (fire) - Power Level: 15, to cast say: foooooooo!, fireball II (fire) - Power Level: 12, to cast say: fireball!!, fireball (fire) - Power Level: 10, to cast say: fireball!]"),
                           "getTopK of K = 3 on the fire category has failed.");

        // top k on different category
        test(table.getTopK("lightning",3).toString().equals("[lightning bolt (lightning) - Power Level: 11, to cast say: go lightning bolt, shockwave II (lightning) - Power Level: 10, to cast say: be useful pikachu., thunderstorm (lightning) - Power Level: 9, to cast say: I`m going to shock you]"),
                           "getTopK of K = 3 on the lightning category has failed.");

        // top k with k > size
        test(table.getTopK("lightning",10).toString().equals("[lightning bolt (lightning) - Power Level: 11, to cast say: go lightning bolt, shockwave II (lightning) - Power Level: 10, to cast say: be useful pikachu., thunderstorm (lightning) - Power Level: 9, to cast say: I`m going to shock you, shockwave (lightning) - Power Level: 8, to cast say: go pikachu!]"),
                           "getTopK of K = 10 on the lightning category has failed.");

        // top k on a category that doesn't exist
        test(table.getTopK("void",3) == null, "getTopK of K = 3 on the fire category has failed.");

        // search spell
        test(table.searchSpell("fire","fireball",  10).toString().equals("fireball (fire) - Power Level: 10, to cast say: fireball!"), "The spell should've been, instead got: '" + table.searchSpell("fire","fireball",  10) + "'");

        // search for a spell that doesn't exists
        test(table.searchSpell("fire","firestone",  10) == null, "The spell should've been, instead got: '" + table.searchSpell("fire","firestone",  10) + "'");

        // search for a category that doesn't exists
        test(table.searchSpell("void","fireball",  10) == null, "The spell should've been, instead got: '" + table.searchSpell("void","fireball",  10) + "'");
    }
}
