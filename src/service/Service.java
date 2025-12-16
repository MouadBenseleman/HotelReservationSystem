package service;

import entities.Booking;
import entities.Room;
import entities.User;
import enums.RoomType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Service {

    private static final Logger logger = Logger.getLogger(Service.class.getName());

    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<User> users =new ArrayList<>();
    ArrayList<Booking> bookings =new ArrayList<>();


    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight){

        if (roomPricePerNight < 0){
            throw new IllegalArgumentException("Room price cannot be negative");
        }

        Room existingRoom = findRoomNumber(roomNumber);
        if (existingRoom != null){
            existingRoom.setRoomType(roomType);
            existingRoom.setPricePerNight(roomPricePerNight);
        }
        else {
            rooms.add(0,new Room(roomNumber,roomType,roomPricePerNight));
        }


    }

    public void setUser(int userId, int balance){

        User existingUser = findUserByIdUser(userId);
        if (existingUser != null) {
            existingUser.setBalance(balance);
        }else {
            users.add(0,new User(userId,balance));
        }


    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut){

        try{
        if (checkIn == null || checkOut == null){
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }

        Calendar calCheckIn = Calendar.getInstance();
        calCheckIn.setTime(checkIn);
        calCheckIn.set(Calendar.HOUR_OF_DAY, 0);
        calCheckIn.set(Calendar.MINUTE, 0);
        calCheckIn.set(Calendar.SECOND, 0);
        calCheckIn.set(Calendar.MILLISECOND, 0);

        Calendar calCheckOut = Calendar.getInstance();
        calCheckOut.setTime(checkOut);
        calCheckOut.set(Calendar.HOUR_OF_DAY, 0);
        calCheckOut.set(Calendar.MINUTE, 0);
        calCheckOut.set(Calendar.SECOND, 0);
        calCheckOut.set(Calendar.MILLISECOND, 0);

        if (!calCheckOut.after(calCheckIn)){
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        User user = findUserByIdUser(userId);
        if (user == null){
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        Room room = findRoomNumber(roomNumber);
        if (room == null ){
            throw new IllegalArgumentException("Room with number " + roomNumber + " not found");
        }

        int numberOfNights = calculateNights(calCheckIn, calCheckOut);
        int totalCost = numberOfNights * room.getPricePerNight();

        if (user.getBalance() < totalCost){
            throw new IllegalArgumentException("Insufficient balance. Required: " + totalCost + ", Available: " + user.getBalance());
        }
        if (!isRoomAvailable(roomNumber, calCheckIn.getTime(), calCheckOut.getTime())) {
            throw new IllegalArgumentException("Room " + roomNumber + " is not available for the specified period");
        }

        user.deductBalance(totalCost);
        bookings.add(0, new Booking(user, room, calCheckIn.getTime(), calCheckOut.getTime(), totalCost, numberOfNights));

        logger.log(Level.INFO, "Booking successful: userId={0}, roomNumber={1}, nights={2}, cost={3}",
                    new Object[]{userId, roomNumber, numberOfNights, totalCost});

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        return bookings.stream()
                .filter(b -> b.getRoomNumber() == roomNumber)
                .noneMatch(b ->
                        checkIn.before(b.getCheckOut()) &&
                                checkOut.after(b.getCheckIn())
                );
    }

    private int calculateNights(Calendar checkIn, Calendar checkOut) {
        long diffMillis = checkOut.getTimeInMillis() - checkIn.getTimeInMillis();
        return (int) (diffMillis / (1000 * 60 * 60 * 24));
    }

    public void printAll(){
        System.out.println(" List of rooms from Latest to the older");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }

        System.out.println(" List of bookins from Latest to the older");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }

    }

    public void printAllUsers(){
        System.out.println("List of users from Latest to the older ");
        if (users.isEmpty()){
            System.out.println("No users available");
        }else {
            for (User user:users){
                System.out.println(user);
            }
        }
    }
    private Room findRoomNumber(int roomNumber){
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber)
                return room;
        }
        return null;
    }

    private User findUserByIdUser(int userId){
        for (User user:users){
            if (user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }
}
