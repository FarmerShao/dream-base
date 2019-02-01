package farmershao.product.crm.dao.mapper;

import farmershao.product.crm.dao.entity.AdminManager;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.impl.AsyncResultSingle;
import io.vertx.reactivex.ext.asyncsql.AsyncSQLClient;

import java.util.List;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 2:32
 */
public class AdminManagerMapper {

    private AsyncSQLClient client;

    private static final String BASE_SQL = "id, password, phone, last_login_time AS lastLoginTime";

    public AdminManagerMapper(AsyncSQLClient client) {
        this.client = client;
    }

    public Single<AdminManager> find(long id){
        return client
                .rxQueryWithParams(String.format("select %s from admin_manager where id = ? limit 1", BASE_SQL), new JsonArray().add(id))
                .flatMap(resultSet -> {
                    List<JsonObject> rows = resultSet.getRows();
                    AdminManager adminManager = rows.isEmpty()? null : rows.get(0).mapTo(AdminManager.class);
                    return new AsyncResultSingle<>(handler ->
                        handler.handle(Future.succeededFuture(adminManager))
                    );
                });
    }

}
