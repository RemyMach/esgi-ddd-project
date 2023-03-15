package com.example.use_case;

import java.time.LocalDateTime;

public record CreateReservation (
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    String roomId,
    String prospectId,
    int numberOfPeople,
    String[] emailInvitationList,
    String reservationDescription
) {}
