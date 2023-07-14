import java.util.*;

public class BSTree {
	Node root;
	
	public Boolean insert(int k)
	{
		Node insert_node = new Node(k);
		if(this.root == null)
		{
			this.root = insert_node;
			return true;
		}
		
		Node node = root;
		while(true)
		{
			if(node.key > k)
				if(node.left!=null)
					node = node.left;
				else
				{
					node.setLeft(insert_node);
					return true;
				}
			else
				if(node.key == k)
					return false;
				else
					if(node.right!=null)
						node = node.right;
					else
					{
						node.setRight(insert_node);
						return true;
					}
		}
	}




	@Override
	public String toString() {
		return root.toString();
	}




	public class Node
	{
		private Node left;
		private Node right;
		private Integer key;
		
		public Node(int key) {
			this.key = key;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		@Override
		public String toString() {
			
			String left_tree="";
        	String right_tree="";
        	String key = "";
        	if(this.left!=null)
        		left_tree = "(" + this.left.toString() + ")";
        	if(this.right!=null)
        		right_tree = "(" + this.right.toString() + ")";
        	if(this != null)
        		key = this.key.toString();
        	return left_tree + key + right_tree;
		}
	}

	
	
	public static void main(String[] args) {
    	for(int i=1;i<=5;i++)
    	{
    		System.out.println("i: " + i);
    		BSTree tree = new BSTree();
    		double time_sum=System.nanoTime();
    		for(int j=0;j<1000*i;j++)
    		{
    			tree.insert(j);
    		}
    		time_sum = System.nanoTime() - time_sum;
    		System.out.println(time_sum/(1000*i));
    	}
    	
    	for(int i=1;i<=5;i++)
    	{	
    		System.out.println("i: " + i);
    		BSTree tree = new BSTree();
    		double time_sum=System.nanoTime();
    		for(int j=1;j<1024*i/2;j *= 2)
    		{
    			for(int k=1;k<j;k++)
    			{
    				tree.insert(1024*i/j*k);
    			}
    		}
    		time_sum = System.nanoTime() - time_sum;
    		System.out.println(time_sum/(1000*i));
    	}
    	
    	for(int i=1;i<=5;i++)
    	{
    		System.out.println("i: " + i);
    		BSTree tree = new BSTree();
    		double time_sum=System.nanoTime();
    		Random rand = new Random();
    		for(int j=0;j<1000*i;j++)
    		{
    			tree.insert(rand.nextInt());
    		}
    		time_sum = System.nanoTime() - time_sum;
    		System.out.println(time_sum/(1000*i));
    	}
    }
}
