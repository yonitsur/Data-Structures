public class Tests {

    public boolean empty() {
        AVLTree avlTree = new AVLTree();
        if (!avlTree.empty()) {
            return false;
        }
        avlTree.insert(1, true);
        return (!avlTree.empty());
    }

    public boolean search() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.search(1) != null) {
            return false;
        }
        avlTree.insert(1, true);
        if (avlTree.search(1) == true) {
            return true;
        }
        return false;
    }

    public boolean insert_and_size() {
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 1000; i++) {
            avlTree.insert(i, true);
        }
        return (avlTree.size() == 1000);
    }

    public boolean delete() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.delete(1) != -1) {
            return false;
        }
//        for (int i = 0; i < 100; i++) {
//            avlTree.insert(i, "num" + i);
//        }
        return true;
    }

    public boolean min() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.min() != null) {
            return false;
        }
        avlTree.insert(0, true);
        for (int i = 1; i < 100; i++) {
            avlTree.insert(i, false);
        }
        return avlTree.min();
    }

    public boolean max() {
        AVLTree avlTree = new AVLTree();
        if (avlTree.max() != null) {
            return false;
        }

        avlTree.insert(100, true);
        for (int i = 0; i < 100; i++) {
            avlTree.insert(i, false);
        }
        return avlTree.max();
    }

    public boolean min_equals_max() {
        AVLTree avlTree = new AVLTree();
        avlTree.insert(1, true);
        return (avlTree.min() == avlTree.max());
    }

    public boolean keysToArray() {
        AVLTree avlTree = new AVLTree();
        boolean infoarray[];
        int[] keysarray;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0){
                avlTree.insert(i, true);
            }
            else{
                avlTree.insert(i, false);
            }

        }
        keysarray = avlTree.keysToArray();
        infoarray = avlTree.infoToArray();
        for (int i = 0; i < 100; i++) {
            if (keysarray[i] != i){
                return false;
            }
            if (i % 2 == 0){
                if (!(infoarray[i])) {
                    return false;
                }
            }
            else{
                if (infoarray[i]) {
                    return false;
                }
            }
        }
        return true;

    }

    public boolean size() {
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 100; i++) {
            avlTree.insert(i, true);
        }
        for (int i = 0; i < 50; i++) {
            avlTree.delete(i);
        }
        for (int i = 0; i < 25; i++) {
            avlTree.insert(i, true);
        }
        return (avlTree.size() == 75);
    }

    public boolean successor(){
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 10; i++) {
            avlTree.insert(i, true);
        }
        AVLTree.AVLNode node = avlTree.getRoot();
        if (node.getKey() != 3) {
            return false;
        }
        node = avlTree.successor(node);
        if (node.getKey() != 4) {
            return false;
        }
        return true;
    }

    public boolean succPrefixXor(){
        AVLTree avlTree = new AVLTree();
        Boolean result = null;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0){
                avlTree.insert(i, true);
            }
            else{
                avlTree.insert(i, false);
            }
        }


        for (int i = 4; i <= 100; i = i + 4) {
            result = avlTree.succPrefixXor(i);
            if (i % 4 == 0){
                if (result){
                    System.out.println("== succPrefixXor failed for : " + i + ". result was " + result);
                    return false;
                }
            }
            else {
                if ((i % 2 == 1) && ((i - 1) % 4 == 0)) {
                    if (result) {
                        System.out.println("== succPrefixXor failed for : " + i + "result was " + result);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean prefixXor(){
        AVLTree avlTree = new AVLTree();
        Boolean result = null;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0){
                avlTree.insert(i, true);
            }
            else{
                avlTree.insert(i, false);
            }
        }


        for (int i = 4; i <= 100; i = i + 4) {
            result = avlTree.prefixXor(i);
            if (i % 4 == 0){
                if (result){
                    System.out.println("== succPrefixXor failed for : " + i + ". result was " + result);
                    return false;
                }
            }
            else {
                if ((i % 2 == 1) && ((i - 1) % 4 == 0)) {
                    if (result) {
                        System.out.println("== succPrefixXor failed for : " + i + "result was " + result);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean select() {
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < 1000; i++) {
            if (i == 500){
                avlTree.insert(i, true);
            }
            else{
                avlTree.insert(i, false);
            }

        }
        return avlTree.search(500);
    }

//    public boolean avlNodeFuncsImplemented() {
//        AVLTree avlTree = new AVLTree();
//        avlTree.insert(1, "1");
//        ActualAVLTree.IAVLNode avlNode = (ActualAVLTree.IAVLNode) avlTree.getRoot();
//        return true;
//    }

    public boolean checkBalanceOfTree(AVLTree.AVLNode current) {
        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;
        if (current.getRight() != null) {
            balancedRight = checkBalanceOfTree(current.getRight());
            rightHeight = getDepth(current.getRight());
        }
        if (current.getLeft() != null) {
            balancedLeft = checkBalanceOfTree(current.getLeft());
            leftHeight = getDepth(current.getLeft());
        }

        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }

    private int getDepth(AVLTree.AVLNode n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.getRight() != null)
            rightHeight = getDepth(n.getRight());
        if (n.getLeft() != null)
            leftHeight = getDepth(n.getLeft());

        return Math.max(rightHeight, leftHeight) + 1;
    }

    private boolean checkOrderingOfTree(AVLTree.AVLNode current) {
        if (current.getLeft().isRealNode()) {
            if (current.getLeft().getKey() > current.getKey())
                return false;
            else
                return checkOrderingOfTree(current.getLeft());
        } else if (current.getRight().isRealNode()) {
            if (current.getRight().getKey() < current.getKey())
                return false;
            else
                return checkOrderingOfTree(current.getRight());
        } else if (!current.getLeft().isRealNode() && !current.getRight().isRealNode())
            return true;

        return true;
    }

    public boolean testRemove() {
        AVLTree tree = new AVLTree();
        if (!tree.empty()) {
            return false;
        }
        int[] values = new int[]{16, 24, 36, 19, 44, 28, 61, 74, 83, 64, 52, 65, 86, 93, 88};
        boolean[] bool_values = new boolean[]{true, false, false, false, false, false, false, false, false, false, false, false, false, true, false};
        for (int i = 0 ; i != values.length ; i++) {
            tree.insert(values[i], bool_values[i]);
        }
        
        
        
        if (!(tree.min())) {
            return false;
        }
        if (!(tree.max())) {
            return false;
        }
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
            return false;
        }
        tree.delete(88);
        if (!checkBalanceOfTree(tree.getRoot())) {
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        
            return false;
        }
        if (tree.search(88) != null) {
        	
            return false;
        }

        tree.delete(19);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(19) != null) {
        	
            return false;
        }

        tree.delete(16);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(16) != null) {
        	
            return false;
        }

        tree.delete(28);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(16) != null) {
        	
            return false;
        }
        tree.delete(24);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(24) != null) {
        	
            return false;
        }

        tree.delete(36);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(36) != null) {
        	
            return false;
        }

        tree.delete(52);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(52) != null) {
        	
            return false;
        }

        tree.delete(93);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(93) != null) {
        	
            return false;
        }

        tree.delete(86);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(86) != null) {
        
            return false;
        }

        tree.delete(83);
        if (!checkBalanceOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (!checkOrderingOfTree(tree.getRoot())) {
        	
            return false;
        }
        if (tree.search(83) != null) {
        	
            return false;
        }
        return true;
    }

}
