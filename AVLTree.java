import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * public class AVLNode
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
 * arguments. Changing these would break the automatic tester, and would result in worse grade.
 * <p>
 * However, you are allowed (and required) to implement the given functions, and can add functions of your own
 * according to your needs.
 */

public class AVLTree {

	public final static AVLNode Virtual = new AVLNode();
	private AVLNode root ;
	private int size = 0;
	private static boolean xorFlag = false;

    /**
     * This constructor creates an empty AVLTree.
     */
    public AVLTree(){ 
    	this.root = Virtual; 
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return !this.root.isRealNode(); 
    }

    /**
     * public boolean search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     */
    public Boolean search(int k) {
    	AVLNode node = this.root;
    	while (node.isRealNode()) {
    		if (k == node.key)
    			return node.info;
    		if(k < node.key)
    			node = node.left;
    		else node = node.right; }
    	
    	return null;
    }
    public AVLNode searchNode(int k) {
    	AVLNode node = this.root;
    	while (node.isRealNode()) {
    		if (k == node.key)
    			return node;
    		if(k < node.key)
    			node = node.left;
    		else node = node.right; }
    	
    	return null;
    }

    /**
     * public int insert(int k, boolean i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
	 * returns the number of nodes which require rebalancing operations (i.e. promotions or rotations).
	 * This always includes the newly-created node.
     * returns -1 if an item with key k already exists in the tree.
     */
    
    public int insert(int k, boolean i) {
    	if(this.search(k)!=null)
    		return -1;
    	int counter = 0;
    	
    	if(this.empty()) {
    		this.root = new AVLNode(k,i);
    		this.size++;
    		return counter;	}
    	
    	AVLNode node = this.root;
    	AVLNode newNode = Virtual;
    	while (!newNode.isRealNode()) {
    		if (k < node.key) {
    			if(node.left.isRealNode()) 
    				node = node.left;
    			else {
    				newNode = new AVLNode(k,i);
    				newNode.setParent(node);
    				node.setLeft(newNode); } }
    		if (k > node.key) {
    			if(node.right.isRealNode()) 
    				node = node.right;
    			else {
    				newNode = new AVLNode(k,i);
    				newNode.setParent(node);
    				node.setRight(newNode); } } }    	
    	while(newNode.isRealNode()) {
    		newNode.setHeight(1+Math.max(newNode.left.height, newNode.right.height));
    		newNode.setBF(newNode.left.height-newNode.right.height);
    		newNode.setXor(newNode.info^newNode.left.xor^newNode.right.xor);
    		if(newNode.BF == -2) {
    	    	if(newNode.right.BF == -1) 
    	    		counter+=RR(newNode);
    	    	else if(newNode.right.BF == 1) counter+=RL(newNode); }
    	    else if(newNode.BF == 2) {
    	    	if(newNode.left.BF == 1) counter+=LL(newNode);
    	    	else if(newNode.left.BF == -1) counter+=LR(newNode); }
    	    newNode= newNode.parent; }
    	this.size++;
    	return counter;
    }   
        
    public int LL(AVLNode node) {
    	AVLNode tmp = new AVLNode(node.key, node.info);
		tmp.setRight(node.right);
		tmp.setLeft(node.left.right);
		tmp.setHeight(1+Math.max(node.right.height, node.left.right.height));
		tmp.setBF(node.left.right.height-node.left.height);
		tmp.setXor(tmp.info^tmp.left.xor^tmp.right.xor);
		tmp.setParent(node.left);
		node.left.right.setParent(tmp);
		node.right.setParent(tmp);
		node.left.setRight(tmp);
		node.left.setXor(node.left.info^node.left.left.xor^tmp.xor);
		node.left.setParent(node.parent);
		node.left.setHeight(1+Math.max(node.left.left.height, tmp.height));
		node.left.setBF(node.left.left.getHeight()-tmp.getHeight());
		if(node.key != root.key) {
			if(node.parent.left==node) 
				node.parent.setLeft(node.left);
			else if(node.parent.right==node)
				node.parent.setRight(node.left); }
		else root = node.left;	
		
		return 3;
    }
    public int LR(AVLNode node) {
    	AVLNode tmp = new AVLNode(node.left.key, node.left.info);
    	tmp.setParent(node.left.right);
    	tmp.setLeft(node.left.left);
    	tmp.setRight(node.left.right.left);
    	tmp.setHeight(1+Math.max(tmp.left.height, tmp.right.height));
    	tmp.setBF(tmp.left.height- tmp.right.height);
    	tmp.setXor(tmp.info^tmp.left.xor^tmp.right.xor);
    	node.left.right.left.setParent(tmp);
    	node.left.left.setParent(tmp);
    	node.left.right.setLeft(tmp);
    	node.left.right.setParent(node);
    	node.left.right.setHeight(1+Math.max(node.left.right.left.height, node.left.right.right.height));
    	node.left.right.setBF(node.left.right.left.height- node.left.right.right.height);
    	node.left.right.setXor(node.left.right.info^node.left.right.left.xor^node.left.right.right.xor);
    	node.setLeft(node.left.right);
    	node.setHeight(1+Math.max(node.left.height, node.right.height));
    	node.setBF(node.left.height- node.right.height);
    	node.setXor(node.info^node.left.xor^node.right.xor);
		
		return 2 + LL(node);
    }
    public int RR(AVLNode node) {
    	AVLNode tmp = new AVLNode(node.key, node.info);
		tmp.setLeft(node.left);
		tmp.setRight(node.right.left);
		tmp.setHeight(1+Math.max(node.left.height, node.right.left.height));
		tmp.setXor(tmp.info^tmp.left.xor^tmp.right.xor);
		tmp.setParent(node.right);
		tmp.setBF(node.left.height-node.right.left.height);
		node.left.setParent(tmp);
		node.right.left.setParent(tmp);
		node.right.setLeft(tmp);
		node.right.setParent(node.parent);
		node.right.setXor(node.right.info^tmp.xor^node.right.right.xor);
		node.right.setHeight(1+Math.max(node.right.right.height, tmp.height));
		node.right.setBF(tmp.getHeight()-node.right.right.getHeight());
		if(node.key != root.key) {
			if(node.parent.left==node) 
				node.parent.setLeft(node.right);
			else if(node.parent.right==node)
				node.parent.setRight(node.right); }
		else root = node.right;
		
		return 3;
		
    
    }
    public int RL(AVLNode node) {
    	AVLNode tmp = new AVLNode(node.right.key, node.right.info);
    	tmp.setParent(node.right.left);
    	tmp.setRight(node.right.right);
    	tmp.setLeft(node.right.left.right);
    	tmp.setHeight(1+Math.max(tmp.right.height, tmp.left.height));
    	tmp.setBF(tmp.left.height- tmp.right.height);
    	tmp.setXor(tmp.info^tmp.left.xor^tmp.right.xor);
    	node.right.right.setParent(tmp);
    	node.right.left.right.setParent(tmp);
    	node.right.left.setRight(tmp);
    	node.right.left.setParent(node);
    	node.right.left.setHeight(1+Math.max(node.right.left.right.height, node.right.left.left.height));
    	node.right.left.setBF(node.right.left.left.height- node.right.left.right.height);
    	node.right.left.setXor(node.right.left.info^node.right.left.left.xor^node.right.left.right.xor);
    	node.setRight(node.right.left);
    	node.setHeight(1+Math.max(node.left.height, node.right.height));
    	node.setBF(node.left.height- node.right.height);
    	node.setXor(node.info^node.left.xor^node.right.xor);
    	
		return 2+ RR(node);		
    }
    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) {
    	   	
    	AVLNode node = this.searchNode(k);
    	if(node==null)
    		return -1;
    	int counter = 0;
    	
    	if(!node.left.isRealNode() && !node.right.isRealNode()) {
    		if(node==root) 
    			root = Virtual;
    		else if(node.parent.left == node)
    			node.parent.setLeft(Virtual);
    		else if(node.parent.right == node)
    			node.parent.setRight(Virtual); } 
        else if(node.left.isRealNode() && !node.right.isRealNode()) {
        	if(node==root) {
        		root= node.left;
        		node.left.setParent(Virtual); }
        	else if(node.parent.left==node) 
        		node.parent.setLeft(node.left);
        	else if(node.parent.right==node) 
        		node.parent.setRight(node.left);
        		node.left.setParent(node.parent); } 
        else if(!node.left.isRealNode() && node.right.isRealNode()) {
        	if(node==root) {
        		root= node.right;
        		node.right.setParent(Virtual); }
        	else if(node.parent.left==node) 
        			node.parent.setLeft(node.right);
        	else if(node.parent.right==node)
        			node.parent.setRight(node.right);
        	node.right.setParent(node.parent); } 
        else if(node.left.isRealNode() && node.right.isRealNode()) {
        	AVLNode successor = successor(node);
    		if(successor.right.isRealNode()) 
    			successor.right.setParent(successor.parent);
    		if(successor.parent.right==successor) 
        		successor.parent.setRight(successor.right);
    		else if(successor.parent.left==successor)
    			successor.parent.setLeft(successor.right);
    		
    		successor.parent.setHeight(1+Math.max(successor.parent.left.height, successor.parent.right.height));
    		successor.parent.setBF(successor.parent.left.getHeight()-successor.parent.right.getHeight());
    		successor.parent.setXor(successor.parent.info^successor.parent.left.xor^successor.parent.right.xor);
    		
    		successor.setParent(node.parent);
    		if(node.parent.right==node) 
        		node.parent.setRight(successor);
    		else if(node.parent.left==node)
    			node.parent.setLeft(successor);
    		successor.setRight(node.right);
    		successor.setLeft(node.left);
    		if(node.left.isRealNode())
    			node.left.setParent(successor);
    		if(node.right.isRealNode())
    			node.right.setParent(successor);
    		
    		successor.setHeight(1+Math.max(successor.left.height, successor.right.height));
    		successor.setBF(successor.left.getHeight()-successor.right.getHeight());
    		successor.setXor(successor.info^successor.left.xor^successor.right.xor);
    		
    		if(node==root)
    			root=successor;
        }
       	node = node.parent;
		while(node.isRealNode()) {
			node.setHeight(1+Math.max(node.left.height, node.right.height));
			node.setBF(node.left.getHeight()-node.right.getHeight());
			node.setXor(node.info^node.left.xor^node.right.xor);
			if(node.BF == -2) {
				if(node.right.BF == -1|| node.right.BF == 0) counter+=RR(node);
				else if(node.right.BF == 1) counter+= RL(node); }
			else if(node.BF == 2) {
				if(node.left.BF == 1|| node.left.BF == 0) counter+=LL(node);
				else if(node.left.BF == -1) counter+=LR(node); }
			node= node.parent; }  
	size--;  
    return counter;	
    }
   

    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
    public Boolean min() {  
        return minNode().info; 
    }
    public AVLNode minNode() {
    	AVLNode node = this.root;
    	AVLNode MinNode = Virtual;
    	while (node.isRealNode()) {
    		MinNode=node;
    		node = node.left; }
    		
        return MinNode; 
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */
    public Boolean max() {
        return maxNode().info; 
    }
    
    public AVLNode maxNode() {
    	AVLNode node = this.root;
    	AVLNode MaxNode = Virtual;
    	while (node.isRealNode()) {
    		MaxNode=node;
    		node = node.right; }
    	
        return MaxNode; 
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
    	if(this.empty()) 
    		return new int[0];
        List<Integer> lst = new ArrayList<>();
        keysToList(lst, this.root);    
        int[] arr = new int[this.size];
        int i=0;
        for(int k: lst) {
        	arr[i]=k;
        	i++; }
        return arr;
       
    }
    public void keysToList( List<Integer> lst, AVLNode node){
    	if(node.isRealNode()) {
    		keysToList(lst, node.left);
    		lst.add(node.key);
    		keysToList(lst, node.right); }
    }
    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public boolean[] infoToArray() {
    	if(this.empty()) return new boolean[0];
       boolean[] arr = new boolean[this.size()]; 
        List<Boolean> lst = new ArrayList<>();
        infoToList(lst, this.root);    
        int i=0;
        for(boolean k: lst) {
        	arr[i]=k;
        	i++; }
        return arr;       
       
    }
    public void infoToList( List<Boolean> lst, AVLNode node){
    	if(node.isRealNode()) {
    		infoToList(lst, node.left);
    		lst.add(node.info);
    		infoToList(lst, node.right); }
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     */
    public int size() {
        return this.size; // to be replaced by student code
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     */
    public AVLNode getRoot() {
        return this.root;
    }
    /**
     * public boolean prefixXor(int k)
     *
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     *
     * precondition: this.search(k) != null
     *
     */
    
    public boolean prefixXor(int k){
    	xorFlag = false;
    	return prefixXorRec(this.root, k);
    }
    public boolean prefixXorRec(AVLNode node, int k){
    	if(node.isRealNode()) { 
	    	if(node.key==k) 
	    		return xorFlag^node.info^node.left.xor;
	    	if(node.key>k) 
	    		return prefixXorRec(node.left, k);
	    	xorFlag^=node.info^node.left.xor;
	    	return prefixXorRec(node.right, k);
	    }
    	return xorFlag; 	
    }   

    /**
     * public AVLNode successor
     *
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     */
    
    public AVLNode successor(AVLNode node){
    	if(node.key== maxNode().key) 
    		return null;
        AVLNode successor = node.right;
        if(successor.isRealNode()) {
        	while(successor.left.isRealNode())
        		successor = successor.left;
        	return successor; }
        else{
        	successor = node;
        	while(successor.parent.isRealNode()){
        		if(successor.key > node.key)
        			return successor;
        		successor = successor.parent; } }
        
        return successor;
        
    }
  
    /**
     * public boolean succPrefixXor(int k)
     *
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     *
     * precondition: this.search(k) != null
     */
    public boolean succPrefixXor(int k){
    	boolean flag = false;
    	AVLNode node = this.root;
    	while(node.left.isRealNode())
    		node=node.left;
        while(node!=null && node.key <= k) {
        	flag = flag ^ node.info;
        	node = successor(node); }
        
    	return flag;
    
    }
   
    /**
     * public class AVLNode
     * <p>
     * This class represents a node in the AVL tree.
     * <p>
     * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
     * arguments. Changing these would break the automatic tester, and would result in worse grade.
     * <p>
     * However, you are allowed (and required) to implement the given functions, and can add functions of your own
     * according to your needs.
     */
    public static class AVLNode {
    	private Integer key ;
    	private Boolean info;
    	private AVLNode left=Virtual;
    	private AVLNode right=Virtual;
    	private AVLNode parent=Virtual;
    	private int height=-1 ;
    	private int BF=0;
    	private boolean xor = false;
    	
    	public AVLNode() {}
    	
    	public AVLNode(int key, boolean info) {
    		this.key = key;
    		this.info = info;
    		this.xor = info;
    		this.height = 0;
    	}
    	
    	
        //returns node's key (for virtual node return -1)
        public int getKey() {
        	if(this == Virtual) return -1;
        	else return this.key;
        }

        //returns node's value [info] (for virtual node return null)
        public Boolean getValue() {
            return this.info; 
        }

        //sets left child
        public void setLeft(AVLNode node) {
            this.left=node; 
        }

        //returns left child
		//if called for virtual node, return value is ignored.
        public AVLNode getLeft() {
            return this.left; 
        }

        //sets right child
        public void setRight(AVLNode node) {
            this.right = node; 
        }

        //returns right child 
		//if called for virtual node, return value is ignored.
        public AVLNode getRight() {
            return this.right; 
        }

        //sets parent
        public void setParent(AVLNode node) {
            this.parent = node; 
        }

        //returns the parent 
        //if called for virtual node, return value is ignored.
        public AVLNode getParent() {
            return this.parent; 
        }

        // Returns True if this is a non-virtual AVL node
        public boolean isRealNode() { 
        	return this!=Virtual;
        }

        // sets the height of the node
        public void setHeight(int height) {
            this.height = height; // to be replaced by student code
        }

        // Returns the height of the node (-1 for virtual nodes)
        public int getHeight() {
            return this.height; // to be replaced by student code
        }
        
        public void setBF(int BF) {
        	this.BF = BF;
        }
        public void setXor(boolean xor) {
        	this.xor = xor;
        }
        
    }
    
}

