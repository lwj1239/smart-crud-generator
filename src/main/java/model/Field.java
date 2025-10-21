package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    private AccessModifier accessModifier = AccessModifier.PRIVATE;
    private String type;
    private String name;
    private List<String> annotations;
    private String comment;
}
