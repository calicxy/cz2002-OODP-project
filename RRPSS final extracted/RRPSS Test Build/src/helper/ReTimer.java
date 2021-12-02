package helper;

import control.ReservationManager;
import entity.Reservation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;

/**
 * Class which inherited the default Timer class.
 * It is used to store reservation as a reservation timer.
 *
 * @author Wong Ying Xuan
 * @version 1.0
 * @since 24-10-2021
 */
public class ReTimer extends TimerTask {

    private final ReservationManager reservationManager;


    public ReTimer(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }

    @Override
    public void run() {
        LocalDateTime reservationTime;

        // ZW: Changed the for loop because my code gave me ConcurrentModificationException
        // if the for loop depend directly on activeReservationList.
        int size = reservationManager.getReservationList().size();
        if (size == 0) return;

        int index = 0;
        for (int i = 0; i < size; i++) {
            Reservation re = reservationManager.getReservationList().get(index);
            reservationTime = re.getReservationTime();

            // ZW: >= 120
            if (ChronoUnit.MINUTES.between(reservationTime, LocalDateTime.now()) >= 15) {
                reservationManager.cancelReservation(re);
                continue;
            }
            index++;
        }
    }
}