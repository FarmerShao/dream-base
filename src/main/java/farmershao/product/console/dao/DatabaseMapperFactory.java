package farmershao.product.console.dao;

import farmershao.product.console.dao.mapper.AdminManagerMapper;
import farmershao.product.console.dao.mapper.CategoryMapper;
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
            mappers.put(CategoryMapper.class, new CategoryMapper(client));
        }
    }

    public static <T> T getMapper(Class<T> clazz) {
        return (T) mappers.get(clazz);
    }


}
