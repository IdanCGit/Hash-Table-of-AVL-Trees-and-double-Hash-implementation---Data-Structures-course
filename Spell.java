public class Spell {
    private String name;
    private String category;
    private int powerLevel;
    private String words;

    /**
     * Constructor.  The Spell class constructor.
     * 
     * @param name the name of the spell
     * @param category the category of the spell
     * @param powerLevel the power levevl of the spell
     * @param words the words used to cast the spell
     */
    public Spell(String name, String category, int powerLevel, String words) {
        this.name = name;
        this.category = category;
        this.powerLevel = powerLevel;
        this.words = words;
    }

    /**
     * Getter, returns the name of the spell
     * 
     * @return returns the name of the spell
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter, returns the category of the spell
     * 
     * @return returns the category of the spell
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Getter, returns the power level of the spell
     * 
     * @return returns the power level of the spell
     */
    public int getPowerLevel() {
        return this.powerLevel;
    }

    /**
     * Overriding toString() function to make a custom one
     * 
     * @return returns a string that summarizes the spell
     */
    @Override
    public String toString() {
        return name + " (" + category + ") - Power Level: " + powerLevel + ", to cast say: " + words;
    }
}
