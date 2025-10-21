package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnMeta {
    private String ColumnName;
    private Integer dataType;
    private Boolean isPrimaryKey;
    private Boolean isNullable;

}
