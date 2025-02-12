package com.example.airlinebooking.airline_booking_system.service;

import software.amazon.awssdk.services.s3.S3Client;

public interface S3Service {

    S3Client getS3Client();

    String uploadFileToS3(String filePath, String fileName);
}
