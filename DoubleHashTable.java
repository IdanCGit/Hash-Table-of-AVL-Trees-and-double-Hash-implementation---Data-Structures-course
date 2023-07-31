public class DoubleHashTable {
    private SpellSimple[] table;
    private int capacity;
    private int size;
    private int steps=0;

    /**
     * Constructor for DoubleHashTable class. 
     * Starts with empty table, and capacity of the table is determined by the input capacity.
     * 
     * @param capacity The capacity of the table. (m)
     */
    public DoubleHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new SpellSimple[capacity];
        this.size = 0;
    }

    /**
     * Inserts new spell struct to the table (based on hash functions).
     * 
     * @param spell spell struct, an instance of SpellSimple class
     * 
     * @return Returns True if succeeds or False if not.
     */
    public boolean put(SpellSimple spell) { 
        int index;
        int h1;
        int h2;

        if (this.size >= this.capacity){ // tests if there's place for the new item
            return false;
        }

        this.steps = 0;
        
        h1 = hash1(spell.getName());
        h2 = hash2(spell.getName());

        index = (h1 + this.steps*h2)%this.capacity; // could use 0 instead of steps here, but I'd rather make it more readable here

        //TODO - will get stuck if there's space in the table but the hashing functions are not good
        while (this.table[index] != null){ // keep running until table[index] is not taken (null == empty)
            this.steps++;
            index = (h1 + this.steps*h2)%this.capacity;
        }

        this.table[index] = spell;
        this.size++;
        return true; 
    }

    /**
     * Function that returns the 'words' used to cast the spell, given its 'name'.
     * 
     * @param name the name of the spell
     * 
     * @return Returns the 'words' to cast the spell.
     */
    public String getCastWords(String name) {
        int index;
        int h1;
        int h2;

        this.steps = 0;

        if (this.size == 0){ // tests if the table is empty
            return null;
        }
        
        h1 = hash1(name);
        h2 = hash2(name);

        index = (h1 + this.steps*h2)%this.capacity; // could use 0 instead of steps here, but I'd rather make it more readable here

        //TODO - will get stuck if the spell is not in the table
        while (this.table[index].getName() != name){ // keep running until spell is found
            this.steps++;
            index = (h1 + this.steps*h2)%this.capacity;

            if (this.steps > this.size) { // if the spell doesn't exist in table
                return null;
            }
        }
        return this.table[index].getWords();
    }

    /**
     * Getter, returns the number of spells in the hashtable  (spells that Marlin knows)
     * 
     * @return Returns the number of spells currently in the hashtable.
     */
    public int getSize() {
        return this.size; // return this.size
    }

    /**
     * Getter, returns the number of steps performed in the last 'put' or 'getCastWords' action (where the steps size is defined by h2 hash functin)
     * 
     * @return Returns the number of steps performed in the last 'put' or 'getCastWords' action
     */
    public int getLastSteps() {
        return this.steps;
    } 

    /**
     * Hash function that's used when using 'put' and 'getCastWords' to determine the index location of 'name' in table.
     * 
     * @param name the name of the spell
     * 
     * @return Returns value used to determine the index location of 'name' in table
     */
    private int hash1(String name) {
        int result = 0;
        for (int idx = 0; idx < name.length(); idx++){
            result = result + 31*(int)name.charAt(idx); // int casting transforms char to ASCII
        }
        return (result % this.capacity);
    }

    /**
     * Hash function that's used when using 'put' and 'getCastWords' to determine the step size when table[index] is occupied.
     * 
     * @param name the name of the spell
     * 
     * @return Returns step size that's used when table[index] is occupied 
     */
    private int hash2(String name) {
        int result = 0;
        for (int idx = 0; idx < name.length(); idx++){
            result = result + 13*(int)name.charAt(idx); // int casting transforms char to ASCII
        }
        return (1 + (result % (this.capacity - 2))); 
    }

}