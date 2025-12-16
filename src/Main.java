import enums.RoomType;
import service.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        try {
            Service service = new Service();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
            service.setRoom(3, RoomType.MASTER_SUITE, 3000);

            service.setUser(1, 5000);
            service.setUser(2, 10000);

            Date date1 = sdf.parse("30/06/2026");
            Date date2 = sdf.parse("03/07/2026");
            service.bookRoom(1, 2, date1, date2);

            Date date3 = sdf.parse("07/07/2026");
            Date date4 = sdf.parse("30/06/2026");
            service.bookRoom(1, 2, date3, date4);

            Date date5 = sdf.parse("07/07/2026");
            Date date6 = sdf.parse("08/07/2026");
            service.bookRoom(1, 1, date5, date6);

            Date date7 = sdf.parse("07/07/2026");
            Date date8 = sdf.parse("09/07/2026");
            service.bookRoom(2, 1, date7, date8);

            Date date9 = sdf.parse("07/07/2026");
            Date date10 = sdf.parse("08/07/2026");
            service.bookRoom(2, 3, date9, date10);

            service.setRoom(1, RoomType.MASTER_SUITE, 10000);

            service.printAll();
            service.printAllUsers();

        } catch (Exception e) {
            System.out.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }

    }
}