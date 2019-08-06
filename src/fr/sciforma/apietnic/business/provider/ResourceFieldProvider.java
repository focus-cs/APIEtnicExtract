package fr.sciforma.apietnic.business.provider;

import com.sciforma.psnext.api.Resource;
import fr.sciforma.apietnic.business.model.FieldType;
import fr.sciforma.apietnic.business.model.SciformaField;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class ResourceFieldProvider implements FieldProvider<Resource> {

    private List<SciformaField> fields;

    @PostConstruct
    public void init() {
        fields = new ArrayList<>();
        fields.add(SciformaField.builder().name("Compétence directe").type(FieldType.STRING).build());
        fields.add(SciformaField.builder().name("Compétences directes").type(FieldType.STRING).build());
        fields.add(SciformaField.builder().name("End Date").type(FieldType.DATE).build());
        fields.add(SciformaField.builder().name("ID").type(FieldType.STRING).build());
        fields.add(SciformaField.builder().name("Internal ID").type(FieldType.INTEGER).build());
        fields.add(SciformaField.builder().name("Job Classification").type(FieldType.STRING).build());
        fields.add(SciformaField.builder().name("Métier direct").type(FieldType.STRING).build());
        fields.add(SciformaField.builder().name("Skills").type(FieldType.LIST).build());
        fields.add(SciformaField.builder().name("Start Date").type(FieldType.DATE).build());
        fields.add(SciformaField.builder().name("Status").type(FieldType.STRING).build());
        fields.add(SciformaField.builder().name("User Role").type(FieldType.STRING).build());
    }
}
