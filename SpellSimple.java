public class SpellSimple {
    private String name;
    private String words;

    /**
     * Constructor.  The SpellSimple class constructor.
     * 
     * @param name the name of the spell
     * @param words the words used to cast the spell
     */
    SpellSimple(String name, String words) {
        this.name = name;
        this.words = words;
    }

    /**
     * Getter, returns the name of the spell
     * 
     * @return returns the name of the spell
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter, returns the words of the spell
     * 
     * @return Getter, returns the words of the spell
     */
    public String getWords(){
        return this.words;
    }
}