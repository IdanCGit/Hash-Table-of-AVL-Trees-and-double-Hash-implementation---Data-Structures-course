import java.util.LinkedList;
import java.util.List;

public class HashAVLSpellTable {
    private LinkedList<AVLTree> buckets[];
    private int tableSize;
    private int numSpells;

    /**
     * Constructor, initialize the table where the given size is the number of buckets required
     * 
     * @param size the number of buckets required
     */
    public HashAVLSpellTable(int size) {
        this.numSpells = 0;
        this.tableSize = size;
        this.buckets = new LinkedList[size]; 
    }

    /**
     * Hash function that's used to determine the index location of the category in table
     * 
     * @param category the category of the spell
     * 
     * @return Returns value used to determine the index location of the category in table
     */
    private int hash(String category) {
        int result = 0;
        for (int idx = 0; idx < category.length(); idx++){
            result = result + (int)category.charAt(idx); // int casting transforms char to ASCII
        }
        return (result % this.tableSize);
    }

    /**
     * Adds a spell to the hash table
     * 
     * @param s Spell class instance. The spell to add
     */
    public void addSpell(Spell s) {
        int index = hash(s.getCategory());
        boolean isInserted = false;
        
        if (this.buckets[index] == null) { // slot at index is free - there's place and the spell category is not in the DS yet.
            this.buckets[index] = new LinkedList<AVLTree>(); // create node
        } else { // buckets[index] has an AVLTree there already
            for (AVLTree tree : this.buckets[index]) { // iterate over trees in the same index
                if (tree.getCategory().equals(s.getCategory())) { // if a tree with the wanted category is found
                    tree.insert(s); // add s to the AVL Tree - tree
                    isInserted = true;
                }
            } 
        }
        if (!isInserted) { // didn't find the tree with the same category as spell 's' in buckets[index]
                this.buckets[index].add(new AVLTree(s)); // new AVL tree for the spell, for its category
        }
        this.numSpells++;
    }

    /**
     * Searches for a spell by category, spell name , and powerLevel
     * 
     * @param category The spell's category
     * @param spellName The spell name
     * @param powerLevel The spell's powerLevel
     * 
     * @return returns the searched spell as a Spell class instance, if found, else returns null
     */
    public Spell searchSpell(String category, String spellName, int powerLevel) {
        int index = hash(category);

        for (AVLTree tree : this.buckets[index]) { // iterate over trees in the same index
            if (tree.getCategory().equals(category)) {
                return tree.search(spellName, powerLevel);
            }
        }
        return null;
    }

    /**
     * Getter. Returns the number of spells that exist in the entire data structure (without category).
     * 
     * @return Returns the number of spells that exist in the entire data structure.
     */
    public int getNumberSpells(){
        return this.numSpells;
    }

    /**
     * Getter. Returns the number of spells that exist with the same input category.
     * 
     * @param category The category of spells
     * 
     * @return Returns the number of spells that exist with the same input category.
     */
    public int getNumberSpells(String category){
        int index = hash(category);

        if (this.buckets[index] == null) {
            return 0;
        }

        for (AVLTree tree : this.buckets[index]) { // iterate over trees in the same index
            if (tree.getCategory().equals(category)) {
                return tree.getSize(); // numbere of spells in the AVLTree - tree
            }
        }
        return 0; // return 0 if category was not found
    }

    /**
     * Function that returns the top-k spells (based on power level) with the same input category
     * 
     * @param category The category of spells
     * @param k The number of top spells to return
     * 
     * @return Returns list of top-k spells of the given category.
     */
    public List<Spell> getTopK(String category, int k) {
        int index = hash(category);

        if (this.buckets[index] == null) {
            return null;
        }

        for (AVLTree tree : this.buckets[index]) { // iterate over trees in the same index
            if (tree.getCategory().equals(category)) {
                return tree.getTopK(k); // numbere of spells in the AVLTree - tree
            }
        } 
        return null;
    }
}
