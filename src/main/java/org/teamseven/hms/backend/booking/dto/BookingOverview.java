package org.teamseven.hms.backend.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingOverview {
    private UUID bookingId;
    private String[] slots;
    private BookingType type;
    private String bookingDescription;
    private String bookingDate;
}
