# Gator Taxi
<div align="justify">
GatorTaxi's Ride Management System is an innovative solution designed to efficiently handle a surge in ride requests. By leveraging a custom <b>min-heap</b> and <b>Red-Black Tree (RBT)</b>, the system ensures optimal ride selection and management, prioritizing cost and duration. Key operations such as insertion, cancellation, and updates are seamlessly supported, providing a robust framework for managing the dynamic demands of a ride-sharing service.
</div>

## Execution Guide
Steps to run the Program
```
$ make
$ java gatorTaxi <input_file_name.txt>
```

## Implementation Details

- Utilizes a combination of a **min-heap** and a **Red-Black Tree (RBT)** for efficient ride management.
- Min-heap organizes triplets (rideNumber, rideCost, tripDuration) based on rideCost. Ties are resolved by prioritizing the shortest tripDuration.
- RBT arranges triplets in ascending order according to rideNumber.
- Maintains pointers between corresponding nodes in both the min-heap and RBT for synchronization.
- Engineered to handle up to 2000 active rides efficiently.
- Implementation details report : [Project Report](ProjectReport.pdf)
