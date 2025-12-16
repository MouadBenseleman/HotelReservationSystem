package entities;

import enums.RoomType;

import java.util.Date;

public class Booking {
    private int userId;
    private int userBalanceAtBooking;
    private int roomNumber;
    private RoomType roomTypeAtBooking;
    private int roomPriceAtBooking;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private int numberOfNights;

    public Booking(User user, Room room, Date checkIn, Date checkOut, int totalCost, int numberOfNights) {
        this.userId = user.getUserId();
        this.userBalanceAtBooking = user.getBalance();
        this.roomNumber = room.getRoomNumber();
        this.roomTypeAtBooking = room.getRoomType();
        this.roomPriceAtBooking = room.getPricePerNight();
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.numberOfNights = numberOfNights;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }
}
