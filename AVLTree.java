import java.util.ArrayList;
import java.util.List;

public class AVLTree {

    private Node root;
    private int size;
    private String category;
	
	// private Node class for the AVL Tree nodes
    private class Node { 
        private Spell spell;
        private Node left;
        private Node right;
        private int height;

        private Node(Spell spell) {
            this.spell = spell;
            this.right = null;
            this.left = null;
            this.height = 0;
        }
    }

    // Constructor, getters, setters

    /**
     * Constructor. The AVLTree class constructor.
     * 
     * @param spell Spell class instance
     */
    public AVLTree(Spell spell) {
        this.root = new Node(spell);
        this.size = 1;
        this.category = this.root.spell.getCategory();
    }

    /**
     * Getter, returns the height of the AVL Tree
     * 
     * @return returns the height of the AVL Tree
     */
    public int getTreeHeight(){
        return getNodeHeight(this.root);
    }

    /**
     * Getter, returns the size of the AVL Tree (number of spells)
     * 
     * @return returns the number of spells in the AVL tree.
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Getter, returns the category that the AVL tree represents as a string.
     * 
     * @return returns the category that the AVL tree represents
     */
    public String getCategory() {
        return this.category;
    }


    /**
     * Searches for a spell based on the name and the power level 
     * 
     * @param spellName the name of the spell to locate
     * @param powerLevel the power level of the spell to locate
     * 
     * @return returns the spell of the requested spell name and power level if exists, else returns null
     */
    public Spell search(String spellName, int powerLevel) {
        Node resultSpellNode;
        resultSpellNode = searchHelper(this.root, spellName, powerLevel);
        if (resultSpellNode != null) {
            return resultSpellNode.spell;
        }  
        return null;
    }


    /**
     * Helper function. Recursively searches the tree for the specified spell with the same powerLevel, returns null if not found
     * 
     * @param node Node class instance
     * @param name the name of the spell to locate
     * @param powerLevel the power level of the spell to locate
     * 
     * @return returns the node of the requested spell if exists, else returns null
     */
    private Node searchHelper(Node node, String name, int powerLevel) {
        Node newNode;

        if (node == null) { // No spell with the specified power level was found
            return null;
        }
        
        if ((node.spell.getPowerLevel() == powerLevel) && (node.spell.getName() == name)) { // found same power level and same name - found
            newNode = node; // found
        } else if (node.spell.getPowerLevel() > powerLevel) { // node has higher power level
            newNode = searchHelper(node.left, name, powerLevel); // go left
        } else if (node.spell.getPowerLevel() < powerLevel) { //  - node has lower power level 
            newNode = searchHelper(node.right, name, powerLevel); // go right
        } else { // same power level, different name
            return null;
        }
        return newNode;
    }

    /**
     * Inserts new spell to the AVLTree.
     * 
     * @param spell Spell class instance
     */
    public void insert(Spell spell) {
        this.root = insertHelper(this.root, spell);
    }

    /**
     * Helper function. Used to recursively insert a spell to the AVL Tree rooted under node. 
     * Keeps the AVL Tree's balance throughout the procedure.
     * 
     * @param node Node class instance. The new spell will be rooted under this node.
     * @param newSpell Spell class instance. The new spell to add to the tree.
     * 
     * @return returns a Node class instance used in the recursive operations.
     */
    private Node insertHelper(Node node, Spell newSpell) {

        if (node == null) { // Empty space found, insert new node
            this.size++;
            return new Node(newSpell);
        }

        // Similar to search, looking for an open slot
        if (node.spell.getPowerLevel() > newSpell.getPowerLevel()) {
            node.left = insertHelper(node.left, newSpell); // go left
        } else if (node.spell.getPowerLevel() < newSpell.getPowerLevel()) {
            node.right = insertHelper(node.right, newSpell); // go right
        } else {
            return node; // won't insert duplicates, as we assume there are none
        }

        // Update node height
        node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;

        // Check for balance violation
        int nodeBalance = checkBalance(node);

        // The four possible cases:
        if ((nodeBalance < -1) && (checkBalance(node.left) < 0)) { // LL
            return rotateRight(node);
        } else if ((nodeBalance > 1) && (checkBalance(node.right) > 0)) { // RR
            return rotateLeft(node);
        } else if ((nodeBalance < -1) && (checkBalance(node.left) > 0)) { // LR
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        } else if ((nodeBalance > 1) && (checkBalance(node.right) < 0)) { // RL - 
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Helper function used to determine the balance of a node. 
     * Calculate the height difference between the right child and the left child of the node, while preventing null pointer exception.
     * 
     * @param node Node class instance.
     * 
     * @return returns the height difference between the right child and the left child of the node.
     */
    private int checkBalance(Node node) { // check node != null before calling this func
        if (node == null) {
            return 0;
        }

        int leftHeight = getNodeHeight(node.left);
        int rightHeight = getNodeHeight(node.right);

        return (rightHeight - leftHeight); // ... 1 - right longer, 0 - equal, -1 - left longer ...
    }

    /**
     * Helper-getter function, gets height of node (as defined in class) and prevent null pointer exception
     * 
     * @param node Node calss instance
     * 
     * @return return the node's height as defined in class
     */
    private int getNodeHeight(Node node) {
        if (node == null) {
            return -1;
        } 
        return node.height;
    }

    /**
     * Helper function used to keep the AVL Tree balanced.
     * Left rotates on a given node k2.
     * 
     * @param k2 Node class instance. The given node to rotate on.
     * 
     * @return returns the new root of the given subtree
     */
    private Node rotateRight(Node k2) { // Variables names are as we saw them in class
        Node k1 = k2.left;
        Node B = k1.right;

        k1.right = k2;
        k2.left = B;

        k2.height = Math.max(getNodeHeight(k2.left), getNodeHeight(k2.right)) + 1;
        k1.height = Math.max(getNodeHeight(k1.left), getNodeHeight(k1.right)) + 1;

        // System.out.println("Rotated right!"); 

        return k1;
    }

    /**
     * Helper function used to keep the AVL Tree balanced.
     * Right rotates on a given node k2.
     * 
     * @param k2 Node class instance. The given node to rotate on.
     * 
     * @return returns the new root of the given subtree
     */
    private Node rotateLeft(Node k2) { // Variables names are as we saw them in class
        Node k1 = k2.right;
        Node B = k1.left;

        k1.left = k2;
        k2.right = B;

        k2.height = Math.max(getNodeHeight(k2.left), getNodeHeight(k2.right)) + 1;
        k1.height = Math.max(getNodeHeight(k1.left), getNodeHeight(k1.right)) + 1;

        // System.out.println("Rotated left!"); 

        return k1;
    }

    /**
     * Returns list of top-k spells in the AVL Tree.
     * 
     * @param k The number of top spells to return
     * 
     * @return Returns list of top-k spells in the AVL Tree.
     */
    public List<Spell> getTopK(int k) {
        List<Spell> result = new ArrayList<Spell>();
        Node node = getTopOne(this.root);
        Node tmp;
    
        for (int i = 0; i<k; i++){
            if (node == null) {
                return result;
            }
            
            result.add(node.spell);
            // System.out.println("Added: "+ node.spell.getPowerLevel()); // mute this
            tmp = getPredecessor(this.root, node.spell.getPowerLevel(), null);
            if (tmp == node) {
                return result;
            } else {
                node = tmp;
            }
        }
        return result;
    }

    /**
     * Helper function. Recursively finds and returns the node with the max power level spell in the AVL Tree rooted under the given node.
     * 
     * @param node Node class instance. The root of the AVL Tree/Subtree.
     * 
     * @return returns the node with the max power level spell in the AVL Tree rooted under the given node.
     */
    private Node getTopOne(Node node) {
        Node nextNode = node.right;

        if (nextNode == null) {
            return node;
        } else {
            return getTopOne(nextNode);
        }

    }

    /**
     * Helper function. Recursively finds and returns the predecessor of a given node.
     * 
     * @param node Node class instance. The root of the AVL Tree/Subtree.
     * @param powerLevel power level of the node that we're looking for its predecessor 
     * @param pred Node class instance. The predecessor node that was found up to the specific iteration. At the end it is the predecessor itself.
     * 
     * @return returns the predecessor (Node) of a given node (predecessor of the node with the given power level).
     */
    private Node getPredecessor(Node node, int powerLevel, Node pred) { // powerLevel is needed in order to not lose the node that we're looking for its predecessor
        
        if (node == null) {
            return pred;
        }
    
        if (node.spell.getPowerLevel() == powerLevel) {
            if (node.left != null) {
                pred = getTopOne(node.left);
            } 
            return pred;
        }

        if (node.spell.getPowerLevel() > powerLevel) {
            return getPredecessor(node.left, powerLevel, pred);

        } else {
            pred = node;
            return getPredecessor(node.right, powerLevel, pred);
        }
    }
}


