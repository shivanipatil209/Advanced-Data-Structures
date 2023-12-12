
// Class to store Ride details
class Ride {
    private int rideNumber;
    private int rideCost;
    private int tripDuration;

    public Ride(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    public int getRideCost() {
        return this.rideCost;
    }

    public int getTripDuration() {
        return this.tripDuration;
    }

    public int getRideNumber() {
        return this.rideNumber;
    }

    public void setRideCost(int cost) {
        this.rideCost = cost;
    }

    public void setTripDuration(int duration) {
        this.tripDuration = duration;
    }
}
