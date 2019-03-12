package fr.sciforma.apietnic.business.processor;

import fr.sciforma.apietnic.business.extractor.BooleanExtractor;
import fr.sciforma.apietnic.business.extractor.CalendarExtractor;
import fr.sciforma.apietnic.business.extractor.DateExtractor;
import fr.sciforma.apietnic.business.extractor.DecimalExtractor;
import fr.sciforma.apietnic.business.extractor.Extractor;
import fr.sciforma.apietnic.business.extractor.IntegerExtractor;
import fr.sciforma.apietnic.business.extractor.ListExtractor;
import fr.sciforma.apietnic.business.extractor.StringExtractor;
import fr.sciforma.apietnic.business.factory.ExtractorFactory;
import fr.sciforma.apietnic.business.model.FieldType;
import fr.sciforma.apietnic.business.model.SciformaField;
import fr.sciforma.apietnic.service.SciformaService;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public abstract class AbstractProcessor<T> {

    @Value("${csv.delimiter}")
    protected String csvDelimiter;
    @Value("${csv.path}")
    protected String path;
    @Autowired
    ExtractorFactory<T> extractorFactory;

    Map<FieldType, Extractor<? super T>> extractorMap = new EnumMap<>(FieldType.class);

    @Autowired
    protected StringExtractor stringExtractor;
    @Autowired
    protected DecimalExtractor decimalExtractor;
    @Autowired
    protected BooleanExtractor booleanExtractor;
    @Autowired
    protected DateExtractor dateExtractor;
    @Autowired
    protected IntegerExtractor integerExtractor;
    @Autowired
    protected ListExtractor listExtractor;
    @Autowired
    protected CalendarExtractor calendarExtractor;

    List<SciformaField> fieldsToExtract;
    List<String> csvLines;

    protected abstract String getFilename();
    protected abstract void process(SciformaService sciformaService);

    @PostConstruct
    public void postConstruct() {
        extractorMap.putIfAbsent(FieldType.STRING, stringExtractor);
        extractorMap.putIfAbsent(FieldType.DECIMAL, decimalExtractor);
        extractorMap.putIfAbsent(FieldType.BOOLEAN, booleanExtractor);
        extractorMap.putIfAbsent(FieldType.COST, decimalExtractor);
        extractorMap.putIfAbsent(FieldType.EFFORT, decimalExtractor);
        extractorMap.putIfAbsent(FieldType.DATE, dateExtractor);
        extractorMap.putIfAbsent(FieldType.FORMULA, stringExtractor);
        extractorMap.putIfAbsent(FieldType.DURATION, decimalExtractor);
        extractorMap.putIfAbsent(FieldType.INTEGER, integerExtractor);
        extractorMap.putIfAbsent(FieldType.USER, stringExtractor);
        extractorMap.putIfAbsent(FieldType.RESOURCE, stringExtractor);
        extractorMap.putIfAbsent(FieldType.URL, stringExtractor);
        extractorMap.putIfAbsent(FieldType.CALENDAR, stringExtractor);
        extractorMap.putIfAbsent(FieldType.EFFORT_RATE, stringExtractor);
        extractorMap.putIfAbsent(FieldType.LIST, listExtractor);
    }

    void toCsv() {
        String filePath = path + getFilename();

        try (FileWriter fileWriter = new FileWriter(filePath)) {

            StringJoiner header = new StringJoiner(csvDelimiter);

            for (SciformaField field : fieldsToExtract) {
                header.add(field.getName());
            }

            fileWriter.append(header.toString()).append("\n");

            for (String csvLine : csvLines) {
                fileWriter.append(csvLine).append("\n");
            }

            fileWriter.flush();

        } catch (IOException e) {
            Logger.error(e, "Failed to create file with path " + filePath);
        }
    }
}
