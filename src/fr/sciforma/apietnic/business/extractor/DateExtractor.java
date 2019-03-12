package fr.sciforma.apietnic.business.extractor;

import com.sciforma.psnext.api.FieldAccessor;
import com.sciforma.psnext.api.PSException;
import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
public class DateExtractor<T extends FieldAccessor> implements Extractor<T> {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Optional<String> extract(T fieldAccessor, String fieldName) {

        try {

            return Optional.of(sdf.format(fieldAccessor.getDateField(fieldName)));

        } catch (PSException e) {
            Logger.error(e, "Failed to retrieve date value from field " + fieldName);
        }

        return Optional.empty();
    }

}