package farmershao.product.console.dao.mapper;

import farmershao.product.console.dao.entity.AdminManager;
import farmershao.product.console.verticle.RedisVerticle;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.impl.AsyncResultSingle;
import io.vertx.reactivex.ext.asyncsql.AsyncSQLClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static farmershao.product.console.common.CacheKeyEnum.*;
import static farmershao.product.console.verticle.RedisVerticle.redisClient;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 2:32
 */
public class AdminManagerMapper {

    private final static Logger log = LoggerFactory.getLogger(AdminManagerMapper.class);

    private static final String BASE_SQL = "id, password, phone, last_login_time AS lastLoginTime";

    private AsyncSQLClient client;

    public AdminManagerMapper(AsyncSQLClient client) {
        this.client = client;
    }

    public Single<AdminManager> find(long id) {
        String cacheKey = ADMIN_MANAGER.getKey() + id;
        return RedisVerticle
                .rxGet(cacheKey, AdminManager.class)
                .flatMap(adminManager -> {
                    if (adminManager == null) {
                        return client
                                .rxQueryWithParams(String.format("select %s from admin_manager where id = ? limit 1", BASE_SQL), new JsonArray().add(id))
                                .flatMap(resultSet -> {
                                    List<JsonObject> rows = resultSet.getRows();
                                    AdminManager manager = rows.isEmpty() ? null : rows.get(0).mapTo(AdminManager.class);
                                    if (manager != null) {
                                        redisClient
                                                .rxSet(cacheKey, JsonObject.mapFrom(manager).toString())
                                                .subscribe();
                                    }
                                    return new AsyncResultSingle<>(handler ->
                                            handler.handle(Future.succeededFuture(manager))
                                    );
                                });
                    } else {
                        log.debug("cache hit [find : {}]", id);
                        return Single.just(adminManager);
                    }
                }) ;

    }

    public Single<AdminManager> findByPhone(String phone) {
        String cacheKey = ADMIN_MANAGER_PHONE.getKey() + phone;
        return RedisVerticle
                .rxGet(cacheKey, AdminManager.class)
                .flatMap(adminManager -> {
                    if (adminManager == null) {
                        return client
                                .rxQueryWithParams(String.format("select %s from admin_manager where phone = ? limit 1", BASE_SQL), new JsonArray().add(phone))
                                .flatMap(resultSet -> {
                                    List<JsonObject> rows = resultSet.getRows();
                                    AdminManager manager = rows.isEmpty() ? null : rows.get(0).mapTo(AdminManager.class);
                                    if (manager != null) {
                                        redisClient
                                                .rxSet(cacheKey, JsonObject.mapFrom(manager).toString())
                                                .subscribe();
                                    }
                                    return new AsyncResultSingle<>(handler ->
                                            handler.handle(Future.succeededFuture(manager))
                                    );
                                });
                    } else {
                        log.debug("cache hit [findByPhone : {}]", phone);
                        return Single.just(adminManager);
                    }
                }) ;

    }

}
