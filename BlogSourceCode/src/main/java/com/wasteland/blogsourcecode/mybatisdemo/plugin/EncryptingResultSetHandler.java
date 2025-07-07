package com.wasteland.blogsourcecode.mybatisdemo.plugin;

import com.wasteland.blogsourcecode.mybatisdemo.pojo.User;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
public class EncryptingResultSetHandler implements ResultSetHandler {
    private final ResultSetHandler resultSetHandler;

    public EncryptingResultSetHandler(ResultSetHandler resultSetHandler) {
        this.resultSetHandler = resultSetHandler;
    }

    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        // 使用委托对象处理结果集
        List<Object> result = this.resultSetHandler.handleResultSets(stmt);

        // 假设我们有一个User对象，并且知道密码字段名为"password"
        // 对密码进行“加密”操作（这里只是示例，实际应该是解密）
        if (result instanceof List) {
            List<?> resultList = (List<?>) result;
            for (Object item : resultList) {
                if (item instanceof User) {
                    User user = (User) item;
                    String encryptedPassword = encryptPassword(user.getPhone());
                    user.setPhone(encryptedPassword);
                }
            }
        }

        return result;
    }

    private String encryptPassword(String password) {
        // 这里应该是你的加密逻辑，为了演示，我们使用一个简单的替换逻辑
        return DigestUtils.md5(password);
    }

    @Override
    public <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException {
        return null;
    }

    @Override
    public void handleOutputParameters(CallableStatement cs) throws SQLException {

    }

    // 其他方法...
}
