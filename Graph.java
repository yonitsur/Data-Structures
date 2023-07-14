import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class represents a graph that efficiently maintains the heaviest neighborhood over edge addition and
 * vertex deletion.
 *
 */
public class Graph {
	
	private DLL[] adjacencyList ; 
	private MaxHeapNode[] maxHeap;  	
	private HashTable dict;         
	private int numNodes=0;		
	private int numEdges;	
	
	//**********************************
    // 			Graph constructor
    //**********************************
	/**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     * @param nodes - an array of node objects
     */
    public Graph(Node[] nodes){
    	
        adjacencyList = new DLL[nodes.length];
        dict = new HashTable(nodes.length);
        maxHeap = new MaxHeapNode[nodes.length];
        numNodes = nodes.length;
        
    	for(int i = 0 ; i<nodes.length; i++){
    		maxHeap[i] = new MaxHeapNode(nodes[i]); 
        	adjacencyList[i] = new DLL(nodes[i].id);
        	dict.insert(nodes[i].id, new int[]{i,i});	} 
    	
    	int i = (nodes.length-1)/2;  
    	while(i>=0){
    		heapifyDown(i);			 
      		i--; }                    
    }
    
    //**********************************
    // 			Graph methods
    //**********************************
    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
    	
    	if (numNodes == 0)
    		return null;
    	
        return maxHeap[0].node;
    }
    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     * @param node_id - an id of a node.
     * @return the neighborhood weight of the node of id 'node_id' if such a node exists in the graph.
     * Otherwise, the function returns -1.
     */
    public int getNeighborhoodWeight(int node_id){
    	
    	if(!dict.search(node_id))
    		return -1;
    	
        return maxHeap[dict.get(node_id)[1]].key;
    }
    /**
     * This function adds an edge between the two nodes whose ids are specified.
     * If one of these nodes is not in the graph, the function does nothing.
     * The two nodes must be distinct; otherwise, the function does nothing.
     * You may assume that if the two nodes are in the graph, there exists no edge between them prior to the call.
     * @param node1_id - the id of the first node.
     * @param node2_id - the id of the second node.
     * @return returns 'true' if the function added an edge, otherwise returns 'false'.
     * @return true @implies : @post numEdges = @pre numEdges +1
     **/
    public boolean addEdge(int node1_id, int node2_id){
    	
    	if(node1_id == node2_id || !dict.search(node1_id) || !dict.search(node2_id) 
    			|| adjacencyList[dict.get(node1_id)[0]].dllSearch(node2_id))
    		return false; 
    	
    	adjacencyList[dict.get(node1_id)[0]].insert(node2_id); 
    	adjacencyList[dict.get(node2_id)[0]].insert(node1_id);  	
    	adjacencyList[dict.get(node1_id)[0]].tail.edge = adjacencyList[dict.get(node2_id)[0]].tail; 
    	adjacencyList[dict.get(node2_id)[0]].tail.edge = adjacencyList[dict.get(node1_id)[0]].tail; 
    	
    	maxHeap[dict.get(node1_id)[1]].key += maxHeap[dict.get(node2_id)[1]].node.weight; 
    	maxHeap[dict.get(node2_id)[1]].key += maxHeap[dict.get(node1_id)[1]].node.weight; 
    	
    	heapifyUp(dict.get(node1_id)[1]);
    	heapifyUp(dict.get(node2_id)[1]);
    	
    	numEdges++;
    	
        return true;
    }
    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
    	
    	if(!dict.search(node_id))
    		return false;
    	
    	int index = dict.get(node_id)[1]; 
        swapNodes(index,numNodes -1);
        numNodes--;
        heapifyUp(index);             
        heapifyDown(index); 
        
        DLLNode neighbor = adjacencyList[dict.get(node_id)[0]].head; 
        while(neighbor!=null){
        	DLL neighbor_neighborhood = adjacencyList[dict.get(neighbor.id)[0]];   
        	neighbor_neighborhood.delete(neighbor.edge);                           
        	maxHeap[dict.get(neighbor.id)[1]].key -= maxHeap[numNodes].node.weight;
        	heapifyDown(dict.get(neighbor.id)[1]);                              
        	neighbor=neighbor.next;
        	numEdges--;	}
        
        adjacencyList[dict.get(node_id)[0]]=null;
        dict.remove(node_id);
        
        return true;
    }
    /**
     * given an index i, This method heapify down the node in maxHeap[i] if needed, 
     * and will continue to heapify down the node recursively if needed.
     * [heapify down = swap a node with his largest key child]
     * [needed = when node's key is smaller then his child's key]
     */
    public void heapifyDown(int i) {
    	
    	if((2*i+2)<numNodes && maxHeap[i].key < Math.max(maxHeap[2*i+1].key, maxHeap[2*i+2].key)){
    		if(maxHeap[2*i+1].key > maxHeap[2*i+2].key)
    			 heapifyDown(swapNodes(i, 2*i+1));
    		else heapifyDown(swapNodes(i, 2*i+2));   }
    	else if((2*i+1)<numNodes && maxHeap[i].key < maxHeap[2*i+1].key)
    		heapifyDown(swapNodes(i, 2*i+1));			
   	}
    /**
     * given an index i, This method will heapify up maxHeap[i] if needed, and will continue to heapify up recursively if needed.
     * [heapify up = swap a node with his parent]
     * [needed = when node's key is bigger then his parent's key]
     */
    public void heapifyUp(int i) {
    	
    	if(i>0 && maxHeap[(i-1)/2].key < maxHeap[i].key) 
    		heapifyUp(swapNodes(i, (i-1)/2));
    }
    /**
     * given indexes i,j this method swap the node in maxHeap[i] with the node in maxHeap[j]. 
     * and then update dict-  for each node put the new index in maxheap.
     * @param i, j are indexes of maxHeap
     * @return j. (return value is not "self-need" of the method. it used for the recursive heapify process)   
     */
    public int swapNodes(int i,int  j) {
    	
		MaxHeapNode tmp = maxHeap[j];
		maxHeap[j]= maxHeap[i];
		maxHeap[i] = tmp;
		
		dict.get(maxHeap[i].node.id)[1]=i;	
		dict.get(maxHeap[j].node.id)[1]=j;
		
		return j;
    }
	/**
	 * Returns the number of nodes currently in the graph.
	 * @return the number of nodes in the graph.
	 */
    public int getNumNodes(){
    	
		return numNodes;
	}
	/**
	 * Returns the number of edges currently in the graph.
	 * @return the number of edges currently in the graph.
	 */
    public int getNumEdges(){
    	
		return numEdges;
	}
    public MaxHeapNode[] getMaxHeap() {
		return maxHeap;
	}
    public DLL[] getAdjacencyList(){
		return adjacencyList;
	}
    public HashTable getDict(){
		return dict;
	}
    //**********************************
    // 		   inner Classes
    //**********************************
    
    /************************
     * This class represents a node in the graph.
     *************************/
    public static class Node{
    	
        private int id;
        private int weight;
        
        /**
        * Creates a new node object, given its id and its weight.
        * @param id - the id of the node.
        * @param weight - the weight of the node.
        */
        public Node(int id, int weight){
        	
        	this.id = id;
            this.weight = weight;
        }
        /**
        * Returns the id of the node.
        * @return the id of the node.
        */
        public int getId(){
        	
        	return id;
        }
        /**
        * Returns the weight of the node.
        * @return the weight of the node.
        */
        public int getWeight(){
        	
        	return weight;
        }
        //public String toString(){
        	//return "("+ id + ","+ weight+ ")";
       	//}
   }
    /************************
     * This class represents a node in MaxHeap.
     *************************/
    public class MaxHeapNode{
    	
	    private int key;
        private Node node;
        
        /**
         * Creates a new MaxHeapNode given a node.
         * @post key = "environment weight" = node's weight + node's neighbors weight 
         * @post node = the node that is attributed to key
         */        	
        public MaxHeapNode(Node node) {
        	
        	this.key=node.weight;  
        	this.node = node;
        }
        //public String toString(){
        //	return "("+ key + ","+ node+ ")";
        //}
    } 	
    /************************
     * This class represents a Double Linked List.
     *************************/    
    public  class DLL{
    	
        private DLLNode head=null;
        private DLLNode tail=null;
        private int len = 0;
        private int id;
        
        /**
         * Creates a new Double Linked List given id.
         * if this list represent a node's neighbors in adjacencyList, @param id = node's id. 
         * if this list is a chain of keys in hash table, id has no meaning.
         * (anyway id is not needed and can be removed)
         */     
        public DLL(int id) {
        	
        	this.id = id; 
        	
        }
        /**
         * given k, this method insert a new DLLNode with id=k to the list
         * @post this.search(k) == true
         */	
        public void insert(int k) {
        	
        	if(dllSearch(k))   //optional: insert k only if not exist already in the list
        		return;  
        		
        	DLLNode newNode = new DLLNode(k);
        	if(len==0){ 
        		head = newNode;
        		tail = newNode;	} 				
        	else{
        		newNode.prev = this.tail;
        		tail.next = newNode;
        		tail = newNode;	}
        	
        	len++;  
        }
        /**
         * given a DLLNode, this method delete it from the list.
         * @pre  nums of distinct nodes are distinct
         * @post this.search(node.id) == false 
         */	
        public void delete(DLLNode node) {
        	
        	if(node==null||!dllSearch(node.id)) 
        		return;
        	
        	if(node == head) 
        		head = node.next;
       		if(node == tail) 
       			tail = node.prev;  
            if(node.next!=null)
               	node.next.prev = node.prev;
            if(node.prev!=null)
                node.prev.next = node.next;
            
            len--;  
        }
        /**
         * given i, this method check if there exist a DLLNode in the list with id=i.
         * @return true if there exist.  
         * @return false if there isn't exist.
         */
        public boolean dllSearch(int i) {
        	
        	DLLNode node = head;
        	while(node!=null) {
        		if(node.id==i)
        			return true;
        		node=node.next; }
        	
            return false;
        }
        /**
         * this method check if list is empty.
         * @return true if list is empty.  
         * @return false if list is not empty.
         */	
        public boolean empty() {
        	
        	return len == 0;
        }  
        /*public String toString() {
    	    List<Integer> lst = new ArrayList<>();
    	    DLLNode n = head;
    	    while(n!=null) {
    	    	lst.add(n.id);
    	    	n=n.next;
    	    }
    	    return lst.toString();
    	}*/
    }
    /************************
     * This class represents a node in Double Linked List.
     *************************/  
    public class DLLNode{
    	
    	private int id;                       
    	private int[] indexes = new int[2];  
    	private DLLNode next = null;
    	private DLLNode prev = null;
    	private DLLNode edge = null; 
    	
    	/**
         * Creates a new node of a Double Linked List given id.
         * @pre this list represent a node's neighbors in adjacencyList.
         * @post id == node's id.
         */ 
    	public DLLNode(int id){
    		
    	    this.id=id;
    	}
    	//public String toString() {
    	//   	return ""+id;
    	//}
    }
    /************************
     * This class represents a hash table.
     *************************/  
    public class HashTable{
    	
    	private DLL[] table;
    	private int N;
		private int p = (int) Math.pow(10, 9)+9;
    	private long a;
    	private long b;
    	
    	/**
         * given n, creates a new has table of size n.
         */     		
    	public HashTable(int n) {
    		
    		N = n;
    		table = new DLL[N];
    		for(int i=0;i<N; i++) 
    			table[i] = new DLL(i);
    		b = new Random().nextInt(p);
    		a = 1 + new Random().nextInt(p-1);
    	}
    	/**
         * given a graph node's id and a 2-size array indexes, this method insert id to the table and set it's indexes. 
         * the node of that id is represented in the table by a DLLNode with num=id , and indexes=indexes.
         * (indexes holds the indexes of that node in adjacencyList and in maxHeap). 
         * the method get the index of id in the table by calling hash(id), and then insert id to the DLL in table[hash(id)].
         * @post table[hash(id)].search(id) == true
         * @post table[hash(id)].tail.indexes == indexes
         * @post this.get(id)[0]==indexes[0] 
         * @post this.get(id)[1]==indexes[1] 
         * @post id = maxHeap[this.get(id)[1]].node.id = adjacencyList[this.get(id)[0]].id 
         */
    	public void insert(int id, int[] indexes) {
    		
    		if(!search(id)){                              //optional: check if id already in the table. if no- insert to dict. if yes - override indexes/do nothing
    			table[hash(id)].insert(id);
    			table[hash(id)].tail.indexes = indexes; }
   			else{ DLLNode node = table[hash(id)].head;    
   				  while(node.indexes!=indexes){
    					if(node.id==id) node.indexes = indexes;
   						else node = node.next;	}	}
       	}
    	/**
    	 * given id, if id is in the table :
         * 	@return a 2-size array with the node's of that id indexes in adjacencyList and in maxHeap.
         * 	@post this.get(id)[0] =  index of that node in adjacencyList.
         * 	@post this.get(id)[1] =  index of that node in maxHeap.     
         * if id is not in the table : @return null. 
         */ 
    	public int[] get(int id) {
    		
    		if(search(id)) {                     //optional: check first if id is in the table
    			DLLNode node = table[hash(id)].head;   			
    			while(node!=null){
    				if(node.id==id) 
    					return node.indexes;
    				else node = node.next;	} }
   			
    		return null;
    	}
    	/**
         * given id, this method remove it from the table.
         * @post this.search(id)==false
         */  
    	public void remove(int id) {
    		
    		if(search(id)) {                     //optional: check first if id is in the table
    			DLL dll = table[hash(id)];
    			DLLNode node = dll.head;
    			while(node!=null && node.id!=id) 
    				node = node.next;	
    			dll.delete(node);	}
    	}
    	/**
         * given id, this method check if id is in the table.
         * @return true if id is in the table.
         * @return false if id is not in table.
         */ 
    	public boolean search(int id){
    		
    		if(table.length==0)
    			return false;
    		
    		return table[hash(id)].dllSearch(id);
    	}
    	/**
         * this method check if the table is empty.
         * @return true if the table is empty.
         * @return false if the table is not empty.
         */ 
    	public int hash(int i){   
    		
    		return (int)((a*i+b)%p)%N;
    	}
    	/*public void toStr() {
    		for(int i= 0; i<table.length; i++) {
    			if(!table[i].empty()) {
    				DLLNode y = table[i].head;
    				while(y!=null) {
    					System.out.println("id: ("+ y+")---wehight: "+ maxHeap[dict.get(y.id)[1]].node.weight+"---index: " + Arrays.toString(y.indexes)+ "---neighboors: " + adjacencyList[dict.get(y.id)[0]]+ "---hash code: "+ hash(y.id));
    					y=y.next;
    				}
    			}
    		}
    		if(table.length==0)
    			System.out.println("[]");
    	}*/
    }
    /*public boolean isMaxHeap(int[] arr) {
    	for(int i=0; i<arr.length; i++) {
    		if(2*i+1<arr.length && arr[i]<arr[2*i+1] || 2*i+2<arr.length && arr[i]<arr[2*i+2])
    			return false;
       	}
    	return true;
    }
    public void printMaxHeapKeys(MaxHeapNode[] arr, int n) {
        System.out.println(Arrays.toString(getMaxHeapKeys(arr, n)));
    }
    public int[] getMaxHeapKeys(MaxHeapNode[] arr, int n) {
    	int[] b = new int[n];
        for (int i = 0; i < n; i++)
           b[i]=arr[i].key;
        return b;
    }
    public void printMaxHeapId(MaxHeapNode[] arr, int n) {
            for (int i = 0; i < n; i++)
                System.out.print(arr[i].node.id + "=("+arr[i].key+"), ");
            if(n==0)
            	System.out.print("[]");
            System.out.println();
        
    }
    public void printadjacencyListId(DLL[] neighbors, int n) {
    	
    	List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < n; i++) {
        	if(neighbors[i]!=null)
        		lst.add(neighbors[i].id);  
        	else lst.add(null);  
        }
        System.out.println(lst.toString());
    
    }
    public void printMaxHeapNodes(MaxHeapNode[] arr, int n) {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        if(n==0)
        	System.out.print("[]");
        System.out.println();
    }
    public void printadjacencyListId2(DLL[] neighbors, int n) {
    	
        for (int i = 0; i < n; i++) {
        	if(neighbors[i]!=null)
        		System.out.print(neighbors[i].id+": "+ neighbors[i].toString() +", ");  
        	else System.out.print("null, ");  
        }
        System.out.println();
    
    }
    public static boolean check(int a, Node[] arr) {
    	for(int i=0; i<arr.length; i++) {
    		if(arr[i]!=null && arr[i].id==a)
    			return true;
    		if(arr[i]==null) return false;
    	}
    	return false;
    }*/
}

   
    
    




    
    
    
   
    
    /**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //**************************************************
    //					    main
	//**************************************************
    public static void main(String[] args) {
    	for (int i=6; i<22; i++) {
    		int n=(int)Math.pow(2,i);
    		Node[] nodes = new Node[n];
    		for(int j=0; j< n; j++) 
    			nodes[j] = new Node(j,  1); 
    		Graph g = new Graph(nodes);
    		int x,y;
    		for(int j=0; j<n; j++) {
    			do{	x = y = new Random().nextInt(n);
	        		while(y==x)
	        			y = new Random().nextInt(n);
	    		}while(!g.addEdge(x, y));	}    
    		int maxDegree=0;
    		for(int j=0; j<g.adjacencyList.length; j++) {
    			DLL neighbors = g.adjacencyList[j];
    			if(neighbors.len>maxDegree) 
    				maxDegree = neighbors.len; 	}
    		System.out.println("i: "+i );
    		System.out.println("n: "+n );
    		System.out.println("max Degree: "+maxDegree+" (manual check)");
    		//max Degree when weight = 1 for all nodes in graph g
    		System.out.println("max Degree: "+(g.getNeighborhoodWeight(g.maxNeighborhoodWeight().id)-1));
    		System.out.println();	}
    }
}
//*/    
    
    
    




