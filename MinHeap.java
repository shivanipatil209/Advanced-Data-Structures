import java.io.IOException;
import java.util.ArrayList;

// Class Minheap have all operations of minheap
class MinHeap {
    // Using Arraylist to create the heap
    ArrayList<TreeNode> heap = new ArrayList<>();

    public Ride getMinRide() {
        return heap.get(0).getRide();
    }

    public boolean isEmpty() {
        if (heap.size() == 0) {
            return true;
        }
        return false;
    }

    private int getLeftChild(int rideIndex) {
        return (2 * rideIndex) + 1;
    }

    private int getRightChild(int rideIndex) {
        return (2 * rideIndex) + 2;
    }

    private int getParent(int rideIndex) {
        if (rideIndex % 2 == 1) {
            return rideIndex / 2;
        } else {
            return (rideIndex - 1) / 2;
        }
    }

    private void swapElements(int currentRideIndex, int parentRideIndex) {

        TreeNode temp1 = heap.get(currentRideIndex);
        temp1.setMinHeapIndex(parentRideIndex);

        TreeNode temp2 = heap.get(parentRideIndex);
        temp2.setMinHeapIndex(currentRideIndex);

        heap.set(currentRideIndex, temp2);
        heap.set(parentRideIndex, temp1);
    }

    // Inserting node in min heap
    public void insertNode(TreeNode node) {

        node.setMinHeapIndex(heap.size());
        heap.add(node);
        int currentRideIndex = heap.size() - 1;
        int parentIndex = getParent(currentRideIndex);

        // comparing with parent and swapping if required
        while (parentIndex != currentRideIndex
                && (heap.get(currentRideIndex).getRideCost() <= heap.get(parentIndex).getRideCost())) {
            boolean flag = true;
            if (heap.get(currentRideIndex).getRideCost() == heap.get(parentIndex).getRideCost()) {
                if (heap.get(currentRideIndex).getTripDuration() > heap.get(parentIndex).getTripDuration()) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                swapElements(currentRideIndex, parentIndex);
                currentRideIndex = parentIndex;
                parentIndex = getParent(currentRideIndex);
            }
        }

    }

    // Function to remove root
    public Ride removeMin() throws Exception {

        if (isEmpty() == true) {
            return null;
        } else if (heap.size() == 1) {
            Ride minRide = getMinRide();
            heap.remove(0);
            return minRide;
        } else {
            Ride minRide = getMinRide();
            int lastRideIndex = heap.size() - 1;
            TreeNode lastRide = heap.get(lastRideIndex);
            lastRide.setMinHeapIndex(0);
            heap.set(0, lastRide);
            heap.remove(lastRideIndex);
            heapify(0);
            return minRide;
        }
    }

    private void heapify(int currRideIndex) {
        int leftChild = getLeftChild(currRideIndex);
        int rightChild = getRightChild(currRideIndex);

        int smallest = currRideIndex;

        if (leftChild <= heap.size() - 1) {
            if (heap.get(leftChild).getRideCost() < heap.get(currRideIndex).getRideCost()) {
                smallest = leftChild;
            }

            else if (heap.get(leftChild).getRideCost() == heap.get(currRideIndex).getRideCost()) {
                if (heap.get(leftChild).getTripDuration() < heap.get(currRideIndex).getTripDuration()) {
                    smallest = leftChild;
                }
            }
        }

        if (rightChild <= heap.size() - 1) {
            if (heap.get(rightChild).getRideCost() < heap.get(smallest).getRideCost()) {
                smallest = rightChild;
            } else if (heap.get(rightChild).getRideCost() == heap.get(smallest).getRideCost()) {
                if (heap.get(rightChild).getTripDuration() < heap.get(smallest).getTripDuration()) {
                    smallest = rightChild;
                }
            }
        }

        if (smallest != currRideIndex) {
            swapElements(currRideIndex, smallest);
            heapify(smallest);
        }
    }

    // Deleting arbitary node from the min heap
    public void deleteArbitaryNode(int rideIndex) throws Exception {

        int minRideCost = heap.get(0).getRideCost();
        // making rideCOst of ride to be deleted to be less than root
        heap.get(rideIndex).setRideCost(minRideCost - 1);
        int parent = getParent(rideIndex);
        // Taking it to root and then deleting it
        while (heap.get(rideIndex).getRideCost() < heap.get(parent).getRideCost()) {
            swapElements(rideIndex, parent);
            rideIndex = parent;
            parent = getParent(parent);
        }
        removeMin();
    }

    // Decrease tripduration
    public void decreaseTripDuration(int rideIndex, int new_tripDuration) {
        heap.get(rideIndex).getRide().setTripDuration(new_tripDuration);
        int parentIndex = getParent(rideIndex);
        while (rideIndex != 0 && (heap.get(rideIndex).getRideCost() <= heap.get(parentIndex).getRideCost())) {
            boolean flag = true;
            if (heap.get(rideIndex).getRideCost() == heap.get(parentIndex).getRideCost()) {
                if (heap.get(rideIndex).getTripDuration() > heap.get(parentIndex).getTripDuration()) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                swapElements(rideIndex, parentIndex);
                rideIndex = parentIndex;
                parentIndex = getParent(rideIndex);
            }
        }
    }

}
