package io.github.biezhi.profit.utils;

import com.blade.kit.BladeKit;
import com.blade.mvc.Const;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;

import static io.github.biezhi.profit.bootstrap.Constant.DB_NAME;

/**
 * SQLite 数据库操作
 *
 * @author biezhi
 * @date 2018/9/28
 */

@Slf4j
@NoArgsConstructor
public final class DBUtils {

    public static       String DB_PATH;
    public static       String DB_SRC;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            log.error("load sqlite driver error", e);
        }
    }

    /**
     * 测试连接并导入数据库
     */
    public static void importSql(boolean devMode) {
        try {

            DB_PATH = Const.CLASSPATH + File.separatorChar + DB_NAME;
            DB_SRC = "jdbc:sqlite://" + DB_PATH;

            if (devMode) {
                DB_PATH = System.getProperty("user.dir") + "/" + DB_NAME;
                DB_SRC = "jdbc:sqlite://" + DB_PATH;
            }

            log.info("{}Blade dev mode: {}", BladeKit.getStartedSymbol(), devMode);
            log.info("{}Load SQLite db path [{}]", BladeKit.getStartedSymbol(), DB_PATH);
            log.info("{}Load SQLite db src [{}]", BladeKit.getStartedSymbol(), DB_SRC);

            Connection con       = DriverManager.getConnection(DB_SRC);
            Statement  statement = con.createStatement();
            ResultSet  rs        = statement.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='t_options'");
            int        count     = rs.getInt(1);
            if (count == 0) {
                String            cp  = DBUtils.class.getClassLoader().getResource("").getPath();
                InputStreamReader isr = new InputStreamReader(new FileInputStream(cp + "schema.sql"), "UTF-8");

                String sql = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
                statement.executeUpdate(sql);
            }
            rs.close();
            statement.close();
            con.close();
            log.info("{}Database path is: {}", BladeKit.getStartedSymbol(), DB_PATH);
        } catch (Exception e) {
            log.error("Initialize database fail", e);
        }
    }

}