package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entity {
    private String packageName;
    private List<String> imports;
    private List<String> classAnnotations;
    private String className;
    private List<Field> fields;
}
