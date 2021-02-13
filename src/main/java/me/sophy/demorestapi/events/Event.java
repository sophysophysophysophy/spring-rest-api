package me.sophy.demorestapi.events;

import lombok.*;

import java.time.LocalDateTime;


// All : 모든 param 생성자 , no : param 없는 생성자 생성
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Event {
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private Integer id;
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus = EventStatus.DRAFT;


}