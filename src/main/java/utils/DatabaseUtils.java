package utils;

import model.ColumnMeta;
import model.TableMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    private DatabaseUtils(){}

    public static TableMeta parseTable(Connection connection, String tableName) throws SQLException {
        List<ColumnMeta> columnMetas = getColumnMeta(connection, tableName);
        setColumnMetaPrimary(columnMetas, connection, tableName);

        if (columnMetas.isEmpty()) {
            throw new SQLException("Table '" + tableName + "' not found or has no columns.");
        }

        return new TableMeta(tableName, columnMetas);
    }

    private static void setColumnMetaPrimary(List<ColumnMeta> columnMetas, Connection connection, String tableName) throws SQLException {
        List<String> primaryColumnName = getPrimaryColumnNames(connection, tableName);
        columnMetas.forEach(column -> {
            for (String s : primaryColumnName) {
                if (s.equals(column.getColumnName())) {
                    column.setIsPrimaryKey(true);
                    break;
                }
            }
        });
    }

    private static List<String> getPrimaryColumnNames(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableName);
        List<String> primaryColumnNames = new ArrayList<>();
        
        while (primaryKeys.next()) {
            String columnName = primaryKeys.getString("COLUMN_NAME");
            primaryColumnNames.add(columnName);
        }
        
        return primaryColumnNames;
    }

    private static List<ColumnMeta> getColumnMeta(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        List<ColumnMeta> columnMetas = new ArrayList<>();

        try (ResultSet rs = databaseMetaData.getColumns(connection.getCatalog(), connection.getSchema(), tableName, null)) {
            boolean isFirstRow = true;
            while (rs.next()) {
                ColumnMeta columnMeta = new ColumnMeta();

                String columnName = rs.getString("COLUMN_NAME");
                Integer dataType = rs.getInt("DATA_TYPE");
                Boolean isNullable = rs.getBoolean("IS_NULLABLE");

                columnMeta.setColumnName(columnName);
                columnMeta.setDataType(dataType);
                columnMeta.setIsNullable(isNullable);
                columnMetas.add(columnMeta);
            }
        }


        return columnMetas;
    }

    private static void printResult(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        for (int i = 0; i < resultSetMetaData.getColumnCount(); i ++) {
            System.out.print(resultSetMetaData.getColumnName(i + 1) + "\t");
        }
        System.out.println("\n");
        while (rs.next()) {
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i ++) {
                /*
                * 结果集里每一列的实际类型可能不是 String，但 getString 方法会自动把它们转成字符串，
                * 所以你看到的都是字符串格式的数据。实际存储和处理时，类型还是保留的，只是输出时统一变成了字符串。
                * */
                System.out.print(rs.getString(i + 1) + "\t");
            }
            System.out.println("\n");
        }

        rs.close();
    }
}
