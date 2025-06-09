package com.example.TunisiaCars_Back.service;

import com.example.TunisiaCars_Back.entity.Booking;
import com.example.TunisiaCars_Back.entity.Status;
import com.example.TunisiaCars_Back.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Autowired
    private  BookingRepository bookingRepository;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(String id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
            updatedBooking.setId(id);
            return bookingRepository.save(updatedBooking);
        }).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public Booking cancelBooking(String id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isEmpty()) {
            return null;
        }
        Booking booking = bookingOpt.get();
        booking.setStatus(Status.cancelled);  // Utilise l'enum ici
        return bookingRepository.save(booking);
    }


}

