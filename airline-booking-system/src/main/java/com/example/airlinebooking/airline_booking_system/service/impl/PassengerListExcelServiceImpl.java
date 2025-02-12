package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import com.example.airlinebooking.airline_booking_system.repository.FlightRepository;
import com.example.airlinebooking.airline_booking_system.repository.PassengerRepository;
import com.example.airlinebooking.airline_booking_system.service.PassengerListExcelService;
import com.example.airlinebooking.airline_booking_system.service.S3Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PassengerListExcelServiceImpl implements PassengerListExcelService {

    private final S3Service s3Service;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerListExcelServiceImpl(S3Service s3Service,
                                         FlightRepository flightRepository,
                                         PassengerRepository passengerRepository) {
        this.s3Service = s3Service;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    public String generatePassengerListExcel(String flightNumber) {

        String filePath = "reports/Passenger_List_" + flightNumber + ".xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Passenger List");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Passenger Name", "Seat Number", "Age", "Gender"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Fill passenger data
            FlightEntity flight = flightRepository.findFlightEntityByFlightNumber(flightNumber)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));

            List<PassengerEntity> passengers = passengerRepository.findByBooking_Flight_FlightNumber(flightNumber);

            int rowNum = 1;
            for (PassengerEntity passenger : passengers) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(passenger.getPassengerFullName());
                row.createCell(1).setCellValue(passenger.getSeat().getSeatNumber());
                row.createCell(2).setCellValue(passenger.getAge());
                row.createCell(3).setCellValue(passenger.getGender().toString());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Ensure directory exists
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }

            String s3FileName = "passenger-reports/" + "Passenger_List_" + flightNumber + ".xlsx";
            return s3Service.uploadFileToS3(filePath, s3FileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }
}
