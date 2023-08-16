package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("메세지가 없을 때")
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);    // 메세지가 없는 경우
    }

    @Test
    @DisplayName("메세지가 없을 때, 기본 메세지 활용")
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메세지", null);
        assertThat(result).isEqualTo("기본 메세지");     // 메세지가 없어도 기본 메세지가 반환
    }

    @Test
    @DisplayName("arg 사용한 메세지")
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"spring"}, null);
        assertThat(result).isEqualTo("안녕 spring");
    }

    @Test
    @DisplayName("기본,한국어 버전")
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    @DisplayName("영어 버전")
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
