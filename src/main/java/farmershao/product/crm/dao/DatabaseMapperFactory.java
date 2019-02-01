package farmershao.product.crm.dao;

import farmershao.product.crm.dao.mapper.AdminManagerMapper;
import io.vertx.reactivex.ext.asyncsql.AsyncSQLClient;
import org.apache.http.util.Asserts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 3:19
 */
public class DatabaseMapperFactory {

    private static final Map<Class, Object> mappers = new HashMap<>();

    private DatabaseMapperFactory() {}

    public static void init(AsyncSQLClient client){
        Asserts.notNull(client, "AsyncSQLClient");
        synchronized (DatabaseMapperFactory.class) {
            mappers.put(AdminManagerMapper.class, new AdminManagerMapper(client));
        }
    }

    public static <T> T getMapper(Class<T> clazz) {
        return (T) mappers.get(clazz);
    }


}