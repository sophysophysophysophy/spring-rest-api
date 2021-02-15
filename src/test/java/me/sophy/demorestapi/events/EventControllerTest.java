package me.sophy.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {

//    mocking되어있는 dispatcher servlet을 상대로 요청을 보내고, 응답 확인 가능함. 이러한 테스트 만들 수 있음
//    슬라이싱 테스트 (web 관련 bean만 등록하여 테스트 가능)
//    단위 테스트라고 볼 수는 없음. (컨버터, 데이터 메퍼 등이 주어지기 때문에. eventcontroller만 테스트한다고 볼 수 없음..)
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    슬라이스 테스트이기 때문에 web 관련 bean만 등록. 따라서 repository는 새로 생성해주어야 함..
    @MockBean
    EventRepository eventRepository;


    @Test
    public void createEvent() throws Exception {
        Event event = Event.builder()
                .name("spring")
                .description("REST API")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 02, 13, 9, 19))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 03, 1, 12, 20))
                .beginEventDateTime(LocalDateTime.of(2021, 02, 13, 9, 19))
                .endEventDateTime(LocalDateTime.of(2021, 03, 1, 12, 20))
                .basePrice(100)
                .maxPrice(200)
                .free(false)
                .limitOfEnrollment(100)
                .location("강남")
                .build();

        event.setId(10);
        Mockito.when(eventRepository.save(event)).thenReturn(event);
        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)                //accept header 지정
                .content(objectMapper.writeValueAsString(event))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(10)))
                .andExpect(jsonPath("free").value(Matchers.not(true)));
    }


}
