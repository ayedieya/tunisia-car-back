package com.example.TunisiaCars_Back.service;



import com.example.TunisiaCars_Back.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(String id);

    Booking createBooking(Booking booking);

    Booking updateBooking(String id, Booking booking);
    void deleteBooking(String id);
    List<Booking> getBookingsByUserId(String userId);

    Booking cancelBooking(String id);

    //    public Booking updateStatus(String id, String statusStr) {
//        Booking b = bookingRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée"));
//        b.setStatus(Status.valueOf(statusStr.toLowerCase()));
//        return bookingRepository.save(b);
//    }
    void updateStatus(String id, String status);
}
