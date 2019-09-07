package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
final class ConverterDateTime {

    public class ConverterDate implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return StringUtils.isEmpty(source) ? null : LocalDate.parse(source);
        }
    }

    public class ConverterTime implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return StringUtils.isEmpty(source) ? null : LocalTime.parse(source);
        }
    }

}
