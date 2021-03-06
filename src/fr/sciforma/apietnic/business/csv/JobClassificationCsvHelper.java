package fr.sciforma.apietnic.business.csv;

import fr.sciforma.apietnic.business.provider.JobClassificationFieldProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JobClassificationCsvHelper extends AbstractCsvHelper {

    @Value("${filename.jobClassifications}")
    private String filename;

    @Autowired
    private JobClassificationFieldProvider fieldProvider;

}
