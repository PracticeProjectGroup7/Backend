package org.teamseven.hms.backend.admin.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.teamseven.hms.backend.admin.dto.ModifyBookingRequest;
import org.teamseven.hms.backend.admin.dto.ModifyTestRequest;
import org.teamseven.hms.backend.admin.dto.RetrievePatientItem;
import org.teamseven.hms.backend.admin.dto.RetrievePatientsPaginationResponse;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;
import org.teamseven.hms.backend.catalog.dto.CreateDoctorService;
import org.teamseven.hms.backend.catalog.service.CatalogService;
import org.teamseven.hms.backend.user.Role;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.UserRepository;
import org.teamseven.hms.backend.user.dto.CreateHospitalAccountRequest;
import org.teamseven.hms.backend.user.entity.Doctor;
import org.teamseven.hms.backend.user.entity.DoctorRepository;

import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class AdminService  {
    @Autowired private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private CatalogService catalogService;

    @Autowired private PatientRepository patientRepository;

    @Transactional
    public boolean modifyBooking(ModifyBookingRequest modifyBookingRequest) {
        Optional<Booking> existingBooking = bookingRepository
                .findByServiceIdAndReservedDateAndSlot(
                        modifyBookingRequest.getServiceId(),
                        modifyBookingRequest.getNewReservedDate(),
                        modifyBookingRequest.getNewSlot()
                );
        if(existingBooking.isPresent()) {
            throw new IllegalStateException("Unable to modify booking, booking already exists!");
        }

        return bookingRepository
                .updateBooking(
                        modifyBookingRequest.getPatientId(),
                        modifyBookingRequest.getServiceId(),
                        modifyBookingRequest.getOldReservedDate(),
                        modifyBookingRequest.getOldSlot(),
                        modifyBookingRequest.getNewReservedDate(),
                        modifyBookingRequest.getNewSlot()
                ) == 1;
    }

    @Transactional
    public boolean modifyTest(ModifyTestRequest modifyTestRequest) {
        Optional<Booking> existingTest = bookingRepository
                .checkTestExists(
                        modifyTestRequest.getNewReservedDate(),
                        modifyTestRequest.getServiceId()
                );
        if(existingTest.isPresent()) {
            throw new IllegalStateException("Unable to modify test, test already exists!");
        }

        return bookingRepository
                .updateTest(
                        modifyTestRequest.getPatientId(),
                        modifyTestRequest.getServiceId(),
                        modifyTestRequest.getOldReservedDate(),
                        modifyTestRequest.getNewReservedDate()
                ) == 1;
    }

    public UUID createStaffAccount(
            CreateHospitalAccountRequest request
    ) {
        try {
        return transactionTemplate.execute(status -> {
                User user = User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .gender(request.getGender())
                        .address(request.getAddress())
                        .phone(request.getPhoneNumber())
                        .nric(request.getNric())
                        .dateOfBirth(request.getDateOfBirth())
                        .type(request.getRole().name())
                        .role(request.getRole())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();

                User createdUser = userRepository.save(user);

                if (request.getRole() == Role.DOCTOR) {
                    Doctor doctor = createDoctorAccount(user.getUserId(), request);
                    Logger.getAnonymousLogger().info("Creating doctor account "+ doctor);
                    Doctor createdDoctor = doctorRepository.save(doctor);
                    catalogService.createNewService(
                            CreateDoctorService.builder()
                                    .doctorId(createdDoctor.getDoctorId())
                                    .name(createdUser.getName())
                                    .description(createdDoctor.getSpeciality())
                                    .estimatedPrice(createdDoctor.getConsultationFees())
                                    .build()
                    );
                }

            // Creating doctor account Doctor(doctorId=null, userId=08844ec8-7b45-4d99-9464-f3643bca3672, speciality=Ophthalmology, consultationFees=26.5, yearsOfExperience=4, isActive=null, createdAt=null, modifiedAt=null)

                return createdUser.getUserId();
        });
    } catch (Exception e) {
            Logger.getAnonymousLogger().severe("Exception when creating account " + e);
            throw e;
        }
    }

    private Doctor createDoctorAccount(
            UUID userId,
            CreateHospitalAccountRequest request
    ) {
        return Doctor.builder()
                .userId(userId)
                .speciality(request.getSpecialty())
                .consultationFees(request.getConsultationFees())
                .yearsOfExperience(request.getYearsOfExperience())
                .build();
    }

    public RetrievePatientsPaginationResponse getAllPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        int zeroBasedIndexPage = page - 1;
        Page<Patient> patientList = patientRepository.getPatients(Pageable.ofSize(pageSize).withPage(zeroBasedIndexPage));
        return RetrievePatientsPaginationResponse.builder()
                .currentPage(page)
                .totalElements(patientList.getTotalElements())
                .items(patientList.stream().map(it ->
                                RetrievePatientItem.builder()
                                        .userId(it.getUser().getUserId())
                                        .patientId(it.getPatientId())
                                        .firstname(it.getUser().getFirstName())
                                        .lastName(it.getUser().getLastName())
                                        .email(it.getUser().getEmail())
                                        .build()
                        ).toList())
                .build();
    }
}
