package me.sophy.demorestapi.events;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder().build();
        assertThat(event).isNotNull();
    }

//    java bean spec에 맞는 테스트 : 기본 생성자
    @Test
    public void javabean() {
        Event event = new Event();
        String name = "spring";
        event.setName(name);
        String desc = "rest api";
        event.setDescription(desc);
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(desc);

//        중복 제거 : alt + command + v

    }

}