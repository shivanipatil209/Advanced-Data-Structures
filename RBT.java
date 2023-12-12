
// Class for Red Black Tree operations
class RBT {

    // Declaring static variables RED and BLACK for color of the nodes
    static final int RED = 1;
    static final int BLACK = 0;

    private TreeNode root;
    private TreeNode nullNode;

    // Using constructor to initialize RBT
    public RBT() {
        nullNode = new TreeNode();
        nullNode.color = 0;
        nullNode.leftChildNode = null;
        nullNode.rightChildNode = null;
        root = nullNode;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    // Rotate left at given node
    public void leftRotation(TreeNode currNode) {
        TreeNode y = currNode.rightChildNode;
        currNode.rightChildNode = y.leftChildNode;

        if (y.leftChildNode != nullNode) {
            y.leftChildNode.parentNode = currNode;
        }
        y.parentNode = currNode.parentNode;

        // If currNode was rootNode, then y will become root now
        if (currNode.parentNode == null) {
            this.root = y;
        } else if (currNode == currNode.parentNode.leftChildNode) { // Updating children pointer of parent of currNode
            currNode.parentNode.leftChildNode = y; // currNode was leftChild of its parent
        } else {
            currNode.parentNode.rightChildNode = y; // currNode was rightChild of its parent
        }
        // Changing pointers between node y and currNode
        y.leftChildNode = currNode;
        currNode.parentNode = y;
    }

    // Rotate right at given node
    public void rightRotation(TreeNode currNode) {
        TreeNode y = currNode.leftChildNode;
        currNode.leftChildNode = y.rightChildNode;

        if (y.rightChildNode != nullNode) {
            y.rightChildNode.parentNode = currNode;
        }
        y.parentNode = currNode.parentNode;

        // If currNode was rootNode, then y will become root now
        if (currNode.parentNode == null) {
            this.root = y;
        } else if (currNode == currNode.parentNode.rightChildNode) { // Updating children pointer of parent of currNode
            currNode.parentNode.rightChildNode = y; // If currNode was rightChild of its parent
        } else {
            y.parentNode.leftChildNode = y; // If currNode was leftChild of its parent
        }
        y.rightChildNode = currNode;
        currNode.parentNode = y;
    }

    // Function to rebalance and recolor the BST
    private void rebalanceRecolorInsertion(TreeNode newNode) {
        TreeNode uncleNode;
        while (newNode.parentNode.color == 1) {

            // If newNode's parent is leftCHild of it's parent
            if (newNode.parentNode == newNode.parentNode.parentNode.leftChildNode) {
                uncleNode = newNode.parentNode.parentNode.rightChildNode; // uncleNode

                // Case 1: Parent is RED and Uncle is RED
                if (uncleNode.color == 1) {
                    // Recoloring parent, uncle and grandparent. parent-BLACK, uncle-BLACK,
                    // grandparent-RED
                    uncleNode.color = 0;
                    newNode.parentNode.color = 0;
                    newNode.parentNode.parentNode.color = 1;
                    newNode = newNode.parentNode.parentNode;
                }
                // Case 2: Parent is RED and uncle is BLACK
                else {
                    // case 2.1: newNode is rightChild of its parent, perform leftRotation at
                    // parentNode
                    if (newNode == newNode.parentNode.rightChildNode) {
                        newNode = newNode.parentNode;
                        leftRotation(newNode);
                    }

                    newNode.parentNode.color = 0;
                    newNode.parentNode.parentNode.color = 1;
                    // Right Rotation at grandparent
                    rightRotation(newNode.parentNode.parentNode);
                }
            }

            // If newNode's parent is rightChild of it's parent
            else {
                uncleNode = newNode.parentNode.parentNode.leftChildNode; // uncleNode
                // Case 1: Parent is RED and Uncle is RED
                if (uncleNode.color == 1) {
                    // Recoloring parent, unclde and grandparent. parent-BLACK, uncle-BLACK,
                    // grandparent-RED
                    uncleNode.color = 0;
                    newNode.parentNode.color = 0;
                    newNode.parentNode.parentNode.color = 1;
                    newNode = newNode.parentNode.parentNode;
                } else {
                    // case 2.1: newNode is leftChil of its parent, perform rightRotation at
                    // parentNode
                    if (newNode == newNode.parentNode.leftChildNode) {
                        newNode = newNode.parentNode;
                        rightRotation(newNode);
                    }

                    newNode.parentNode.color = 0;
                    newNode.parentNode.parentNode.color = 1;
                    leftRotation(newNode.parentNode.parentNode);
                }
            }
            if (newNode == root) {
                break;
            }
        }
        // Color root of tree as black
        root.color = 0;
    }

    // Inserting node at its correct position in tree
    public boolean insertNode(TreeNode newNode) {

        TreeNode searchnode = searchNode(newNode.key);
        if (searchnode != nullNode) {
            return false;
        }
        // BST Insertion
        newNode.parentNode = null;
        newNode.leftChildNode = nullNode;
        newNode.rightChildNode = nullNode;
        newNode.color = 1;

        TreeNode y = null;
        TreeNode x = this.root;

        // Choosing either left or right subtree and finding parent of newNode
        while (x != nullNode) {
            y = x;
            if (newNode.key < x.key) {
                x = x.leftChildNode;
            } else {
                x = x.rightChildNode;
            }
        }

        // y is parent of x
        newNode.parentNode = y;
        if (y == null) { // Tree was empty, add newNode as root
            root = newNode;
        } else if (newNode.key > y.key) { // adding as rightChild
            y.rightChildNode = newNode;
        } else {
            y.leftChildNode = newNode; // adding newNode as leftChild
        }

        // If newNode is root of BST, no need to do rebalancing and recoloring, simply
        // return tree
        if (newNode.parentNode == null) {
            newNode.color = 0; // Color root node as black
            return true;
        }

        // If grandparent of newNode is null, simply return it
        if (newNode.parentNode.parentNode == null) {
            return true;
        }

        // In remaining all other scenarios, rebalance and recolor the tree
        rebalanceRecolorInsertion(newNode);
        return true;
    }

    public void deleteNode(int key) {
        // Finding the node with the key
        TreeNode node = searchNode(key);
        TreeNode temp, x, y;

        if (node == null) {
            return;
        }

        y = node;
        int yOriginalColor = y.color;

        // Case 1: Node has single child
        // If node has no leftChild
        if (node.leftChildNode == nullNode) {
            x = node.rightChildNode;
            // Switching nodes to make node to be deleted a leaf node
            switchNodes(node, node.rightChildNode);
        }
        // If node has no rightChild
        else if (node.rightChildNode == nullNode) {
            x = node.leftChildNode;
            // Switching nodes to make node to be deleted a leaf node
            switchNodes(node, node.leftChildNode);
        }
        // Find the leaf node to replace with the node
        else {
            temp = node.rightChildNode;
            while (temp.leftChildNode != nullNode) {
                temp = temp.leftChildNode;
            }
            y = temp;
            yOriginalColor = y.color;
            x = y.rightChildNode;
            if (y.parentNode == node) {
                x.parentNode = y;
            } else {
                switchNodes(y, y.rightChildNode);
                y.rightChildNode = node.rightChildNode;
                y.rightChildNode.parentNode = y;
            }

            switchNodes(node, y);
            y.leftChildNode = node.leftChildNode;
            y.leftChildNode.parentNode = y;
            y.color = node.color;
        }
        if (yOriginalColor == 0) {
            rebalanceRecolorDeletion(x);
        }
    }

    // Function to switch nodes
    private void switchNodes(TreeNode node1, TreeNode node2) {
        if (node1.parentNode == null) {
            root = node2;
        }
        // Node1 is left child of its parent
        else if (node1 == node1.parentNode.leftChildNode) {
            node1.parentNode.leftChildNode = node2;
        }
        // Node1 is right child of its parent
        else {
            node1.parentNode.rightChildNode = node2;
        }
        // Updating pointer of node1's parent
        node2.parentNode = node1.parentNode;
    }

    // Rebalancing and recoloring the nodes after deletion of the node
    private void rebalanceRecolorDeletion(TreeNode node) {
        TreeNode temp;
        // Traverse till node is not root and color of node is black
        while (node != root && node.color == 0) {
            // If node is left child of its parent
            if (node == node.parentNode.leftChildNode) {
                temp = node.parentNode.rightChildNode;
                if (temp.color == 1) {
                    // case 1: node's sibling is RED
                    temp.color = 0;
                    node.parentNode.color = 1;
                    leftRotation(node.parentNode);
                    temp = node.parentNode.rightChildNode;
                }
                // Case 2: node's sibling's both children are BLACK
                if (temp.leftChildNode.color == 0 && temp.rightChildNode.color == 0) {
                    temp.color = 1;
                    node = node.parentNode;
                } else {
                    // Case 3: node's sibling's right Child is black
                    if (temp.rightChildNode.color == 0) {
                        temp.leftChildNode.color = 0;
                        temp.color = 1;
                        rightRotation(temp);
                        temp = node.parentNode.rightChildNode;
                    }

                    // case 4: node's sibling's right child is RED
                    temp.color = node.parentNode.color;
                    node.parentNode.color = 0;
                    temp.rightChildNode.color = 0;
                    leftRotation(node.parentNode);
                    node = root;
                }
            }
            // Node is rightChild of its parennt
            else {
                temp = node.parentNode.leftChildNode;
                if (temp.color == 1) {
                    // case 1: node's sibling is RED
                    temp.color = 0;
                    node.parentNode.color = 1;
                    rightRotation(node.parentNode);
                    temp = node.parentNode.leftChildNode;
                }
                // Case 2: node's sibling's both children are BLACK
                if (temp.rightChildNode.color == 0 && temp.rightChildNode.color == 0) {
                    temp.color = 1;
                    node = node.parentNode;
                } else {
                    // Case 3: node's sibling's right Child is black
                    if (temp.leftChildNode.color == 0) {

                        temp.rightChildNode.color = 0;
                        temp.color = 1;
                        leftRotation(temp);
                        temp = node.parentNode.leftChildNode;
                    }

                    // case 4: node's sibling's right child is RED
                    temp.color = node.parentNode.color;
                    node.parentNode.color = 0;
                    temp.leftChildNode.color = 0;
                    rightRotation(node.parentNode);
                    node = root;
                }
            }
        }
        node.color = 0;
    }

    private TreeNode searchNodeHelper(TreeNode node, int key) {
        // Found the node or came across nullNode
        if (node == nullNode || key == node.key) {
            return node;
        }
        // Go in left subtree
        if (key < node.key) {
            return searchNodeHelper(node.leftChildNode, key);
        }
        // Go in right subtree
        return searchNodeHelper(node.rightChildNode, key);
    }

    // Search Node for given key
    public TreeNode searchNode(int key) {
        return searchNodeHelper(this.root, key);
    }

    // Helper function
    public String inOrderHelper(int rideNumber1, int rideNumber2, TreeNode node) {
        String leftString;
        String rightString;

        int currKey = node.getRideNumber();
        if (node.leftChildNode != nullNode) {
            leftString = inOrderHelper(rideNumber1, rideNumber2, node.leftChildNode);
        } else {
            leftString = "";
        }

        if (node.rightChildNode != nullNode) {
            rightString = inOrderHelper(rideNumber1, rideNumber2, node.rightChildNode);
        } else {
            rightString = "";
        }

        if (rideNumber1 <= currKey && currKey <= rideNumber2) {
            return leftString + "(" + String.valueOf(node.getRideNumber()) + "," + String.valueOf(node.getRideCost())
                    + "," + String.valueOf(node.getTripDuration()) + ")," + rightString;
        } else {
            return leftString + rightString;
        }

    }

    // updating trip duration in RBT
    public void updateNode(int key, int new_tripDuration) {
        TreeNode node = searchNode(key);
        Ride ride = node.getRide();
        if (ride != null) {
            ride.setTripDuration(new_tripDuration);
        }
    }

    public String printRide(Ride ride) {
        return "(" + ride.getRideNumber() + "," + ride.getRideCost() + "," + ride.getTripDuration() + ")";
    }

    // Function to print RBT
    public void printHelper(TreeNode root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != nullNode) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.key + "(" + sColor + ")" + "(" + root.getMinHeapIndex() + ")");
            printHelper(root.leftChildNode, indent, false);
            printHelper(root.rightChildNode, indent, true);
        }
    }
}
