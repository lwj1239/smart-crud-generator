import model.ColumnMeta;
import model.TableMeta;
import org.junit.jupiter.api.Test;
import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DataBaseUtilsTest {
    @Test
    void shouldParseSimpleTable() throws SQLException {
        // 准备测试数据
        try (Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "")) {

            // 创建测试表
            conn.createStatement().executeUpdate(
                    "CREATE TABLE `users` (`id` BIGINT PRIMARY KEY, `name` VARCHAR(50))");

            // 执行测试
            TableMeta table = DatabaseUtils.parseTable(conn, "USERS");

            // 验证结果
            assertThat(table.getName()).isEqualTo("USERS");
            assertThat(table.getColumns()).hasSize(2);

            ColumnMeta idColumn = table.getColumns().get(0);
            assertThat(idColumn.getColumnName()).isEqualTo("ID");
            assertThat(idColumn.getIsPrimaryKey()).isTrue();
        }
    }
}
