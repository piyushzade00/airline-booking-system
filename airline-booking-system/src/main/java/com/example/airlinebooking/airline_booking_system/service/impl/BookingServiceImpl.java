package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.booking.BookingRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.booking.BookingResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.BookingMapper;
import com.example.airlinebooking.airline_booking_system.repository.BookingRepository;
import com.example.airlinebooking.airline_booking_system.repository.FlightRepository;
import com.example.airlinebooking.airline_booking_system.repository.UserRepository;
import com.example.airlinebooking.airline_booking_system.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository,BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
        String flightNumber = bookingRequestDTO.getFlightNumber();
        if(flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new ResourceNotFoundException("Flight number is empty");
        }

        String userName = bookingRequestDTO.getUserName();
        if(userName == null || userName.trim().isEmpty()) {
            throw new ResourceNotFoundException("Username is empty");
        }

        FlightEntity flightEntity = flightRepository.findFlightEntityByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        if (bookingRequestDTO.getTravelDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Travel date must be in the future.");
        }

        if (flightEntity.getAvailableSeats() < bookingRequestDTO.getNumberOfSeats()) {
            throw new IllegalArgumentException("Not enough seats available on this flight.");
        }

        if (bookingRepository.findByFlightAndUser(flightEntity, userEntity).isEmpty()) {
            throw new IllegalArgumentException("Duplicate booking detected for the same user on this flight.");
        }

        BigDecimal totalPrice = flightEntity.getPrice()
                .multiply(BigDecimal.valueOf(bookingRequestDTO.getNumberOfSeats()));

        BookingEntity bookingEntity = bookingMapper.toEntity(bookingRequestDTO, flightEntity, userEntity);
        bookingEntity.setTotalPrice(totalPrice);
        bookingEntity.setBookingDate(LocalDateTime.now());

        flightEntity.setAvailableSeats(flightEntity.getAvailableSeats() - bookingRequestDTO.getNumberOfSeats());
        flightRepository.save(flightEntity);

        BookingEntity savedBooking = bookingRepository.saveBookingEntity(bookingEntity);

        return bookingMapper.toResponseDTO(savedBooking);
    }

    @Override
    public BookingResponseDTO getBookingByCode(String bookingCode) {
        if (bookingCode == null || bookingCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking code cannot be null or empty");
        }
        BookingEntity bookingEntity = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with code: " + bookingCode));
        return bookingMapper.toResponseDTO(bookingEntity);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUser(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return bookingRepository.findByUser(user)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookingsByFlight(String flightNumber) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be null or empty");
        }
        FlightEntity flight = flightRepository.findFlightEntityByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found."));

        return bookingRepository.findByFlight(flight)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookingsWithinTimeframe(LocalDateTime start, LocalDateTime end) {
        if(start.isAfter(end))
            throw new IllegalArgumentException("Start date cannot be after end date.");

        return bookingRepository.findByBookingTimeBetween(start, end)
                .stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAllBookings()
                .stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteBookingByCode(String bookingCode) {
        if (bookingCode == null || bookingCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking code cannot be null or empty");
        }
        bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with code: " + bookingCode));

        return bookingRepository.deleteByBookingCode(bookingCode);
    }
}