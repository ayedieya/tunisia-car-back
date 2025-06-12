package com.example.TunisiaCars_Back.service;

import com.example.TunisiaCars_Back.entity.Booking;
import com.example.TunisiaCars_Back.entity.Car;
import com.example.TunisiaCars_Back.entity.Status;
import com.example.TunisiaCars_Back.entity.User;
import com.example.TunisiaCars_Back.repository.BookingRepository;
import com.example.TunisiaCars_Back.repository.CarRepository;
import com.example.TunisiaCars_Back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private  EmailService emailService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    @Override
//    public Booking createBooking(Booking booking) {
//        return bookingRepository.save(booking);
//    }

    public Booking createBooking(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);

        User user = userRepository.findById(savedBooking.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        Car car = carRepository.findById(savedBooking.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Voiture non trouvée"));

        // Construire l'email
        String subject = "Nouvelle Réservation - TunisiaCars";

        String content = String.format(
                "<html>" +
                        "<body>" +
                        "<h2 style='color: #2c3e50;'>Nouvelle Réservation</h2>" +
                        "<p>Une nouvelle réservation a été effectuée avec les détails suivants :</p>" +
                        "<table style='border-collapse: collapse; font-family: Arial, sans-serif;'>" +
                        "  <tr><td style='padding: 8px; font-weight: bold;'>Client:</td><td style='padding: 8px;'>%s %s</td></tr>" +
                        "  <tr><td style='padding: 8px; font-weight: bold;'>Voiture:</td><td style='padding: 8px;'>%s %s</td></tr>" +
                        "  <tr><td style='padding: 8px; font-weight: bold;'>Date de début:</td><td style='padding: 8px;'>%s</td></tr>" +
                        "  <tr><td style='padding: 8px; font-weight: bold;'>Date de fin:</td><td style='padding: 8px;'>%s</td></tr>" +
                        "</table>" +
                        "<br/>" +
                        "<p style='font-size: 0.9em; color: #888;'>TunisiaCars - Système de réservation automatique</p>" +
                        "</body>" +
                        "</html>",
                user.getFirstName(),
                user.getLastName(),
                car.getBrand(),
                car.getModel(),
                savedBooking.getStartDate(),
                savedBooking.getEndDate()
        );
        emailService.sendEmailToAdmin(subject, content);

        return savedBooking;
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
        booking.setStatus(Status.cancelled);
        return bookingRepository.save(booking);
    }

    @Override
//    public Booking updateStatus(String id, String statusStr) {
//        Booking b = bookingRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée"));
//        b.setStatus(Status.valueOf(statusStr.toLowerCase()));
//        return bookingRepository.save(b);
//    }

    public void updateStatus(String id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée"));

        booking.setStatus(Status.valueOf(status.toLowerCase()));
        bookingRepository.save(booking);

        if ("confirmed".equalsIgnoreCase(status) || "cancelled".equalsIgnoreCase(status)) {
            User user = userRepository.findById(booking.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
            String userEmail = user.getEmail();

            String subject = "";
            String message = "";

            if ("confirmed".equalsIgnoreCase(status)) {
                subject = "Votre réservation a été confirmée";
                message = "Bonjour,\n\nVotre réservation du véhicule du "
                        + booking.getStartDate() + " au " + booking.getEndDate()
                        + " a été confirmée.\n\nMerci.";
            } else if ("cancelled".equalsIgnoreCase(status)) {
                subject = "Votre réservation a été annulée";
                message = "Bonjour,\n\nVotre réservation du véhicule du "
                        + booking.getStartDate() + " au " + booking.getEndDate()
                        + " a été annulée.\n\nMerci.";
            }

            emailService.sendEmail(userEmail, subject, message);
        }
    }



}

