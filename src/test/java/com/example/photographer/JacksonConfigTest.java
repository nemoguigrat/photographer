package com.example.photographer;

import com.example.photographer.config.JacksonConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;

import java.time.*;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.params.provider.Arguments.of;

@JsonTest
@Import(JacksonConfig.class)
class JacksonConfigTest {

    @Autowired
    private ObjectMapper mapper;

    @MethodSource("times")
    @ParameterizedTest(name = "{2} => {0} to {1}")
    void serialize_time_to_iso(Temporal time, String expected, String comment) throws Exception {
        assertThat(mapper.writeValueAsString(time)).isEqualTo(expected);
    }

    static Stream<Arguments> times() {
        return Stream.of(
                of(LocalTime.parse("23:59:59.123"), "\"23:59:59.123\"", "local-time with millis"),
                of(LocalTime.parse("23:59:59"), "\"23:59:59\"", "local-time without millis"),
                of(LocalDate.parse("2019-04-26"), "\"2019-04-26\"", "local-date"),
                of(LocalDateTime.parse("2019-04-26T23:59:59.123"), "\"2019-04-26T23:59:59.123\"", "local-date-time with millis"),
                of(LocalDateTime.parse("2019-04-26T23:59:59"), "\"2019-04-26T23:59:59\"", "local-date-time without millis"),
                of(ZonedDateTime.parse("2019-04-26T01:01:01+05:00"), "\"2019-04-26T01:01:01+05:00\"", "zoned-date-time"),
                of(OffsetDateTime.parse("2019-04-26T01:01:01+05:00"), "\"2019-04-26T01:01:01+05:00\"", "offset-date-time")
        );
    }

    @Test
    void ignore_unknown_fields() {
        String json = "{\n\"name\": \"skblab\",\"unknown\": \"ignore it\"}";

        assertDoesNotThrow(() -> mapper.readValue(json, Probe.class));
    }


    @Autowired
    private JacksonTester<Date> dateJson;

    @Test
    void disable_date_write_as_timestamps() throws Exception {
        assertThat(dateJson.write(new Date())).hasJsonPathStringValue("@");
    }


    @Autowired
    private JacksonTester<Immutable> immutableJson;

    @Test
    void deserialize_immutable() throws Exception {
        String json = "{\"value\": \"immutable\"}";
        assertThat(immutableJson.parseObject(json).getValue()).isEqualTo("immutable");
    }

    @Test
    void deserialize_enum_unknown_as_null() throws Exception{
        // given
        var json = "{\"enumProbe\":\"unknown\"}";

        // when
        var result = mapper.readValue(json, Probe.class);

        // then
        assertThat(result.enumProbe).isNull();
    }

    @Test
    void deserialize_enum_lowerCase_value() throws Exception{
        // given
        var json = "{\"enumProbe\":\"test\"}";

        // when
        var result = mapper.readValue(json, Probe.class);

        // then
        assertThat(result.enumProbe).isEqualTo(EnumProbe.TEST);
    }

    @Test
    void deserialize_enum_upperCase_value() throws Exception{
        // given
        var json = "{\"enumProbe\":\"TEST\"}";

        // when
        var result = mapper.readValue(json, Probe.class);

        // then
        assertThat(result.enumProbe).isEqualTo(EnumProbe.TEST);
    }

    static class Probe {
        String name;
        Date date;
        EnumProbe enumProbe;
    }

    enum EnumProbe {
        TEST, TEST_ONE
    }

    @Value
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
    private static class Immutable {
        String value;
    }
}

