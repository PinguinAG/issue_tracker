package ebuero.aatasiei.tracker.model.internal.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime date) {
        if (Objects.isNull(date)) {
            return null;
        }
        final Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date value) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Instant instant = Instant.ofEpochMilli(value.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
