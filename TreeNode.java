
// Creating a node for RBT and minHeap. Node has data, left child, right child
// and parent pointers
// color(either black or red)
class TreeNode {

    TreeNode leftChildNode;
    TreeNode rightChildNode;
    TreeNode parentNode;
    int key;
    int heapIndex;
    Ride ride;
    int color;

    // constructor for node to set data, chidren and color
    public TreeNode(int key, Ride ride, TreeNode leftChildNode, TreeNode rightChildNode, TreeNode parentNode, int color,
            int heapIndex) {
        this.key = key;
        this.ride = ride;
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
        this.parentNode = parentNode;
        this.color = color; // red node
        this.heapIndex = heapIndex;
    }

    // constructor for node with no children
    public TreeNode(Ride ride) {
        this.key = ride.getRideNumber();
        this.ride = ride;

    }

    public TreeNode() {
        super();
    }

    public int getRideNumber() {
        return this.getRide().getRideNumber();
    }

    public int getRideCost() {
        return this.getRide().getRideCost();
    }

    public void setRideCost(int cost) {
        this.ride.setRideCost(cost);
    }

    public int getTripDuration() {
        return this.getRide().getTripDuration();
    }

    public Ride getRide() {
        return this.ride;
    }

    public int getMinHeapIndex() {
        return this.heapIndex;
    }

    public void setMinHeapIndex(int index) {
        this.heapIndex = index;
    }

}
