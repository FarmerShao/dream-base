package farmershao.product.console.dao.mapper;

import farmershao.product.console.dao.entity.Category;
import farmershao.product.console.verticle.RedisVerticle;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.core.impl.AsyncResultSingle;
import io.vertx.reactivex.ext.asyncsql.AsyncSQLClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static farmershao.product.console.common.CacheKeyEnum.CATEGORY_ID;
import static farmershao.product.console.verticle.RedisVerticle.redisClient;

/**
 * @author ShaoYu
 * @since 2019/2/4 0004 上午 11:40
 */
public class CategoryMapper {

    private final static Logger log = LoggerFactory.getLogger(CategoryMapper.class);


    private AsyncSQLClient client;

    public CategoryMapper(AsyncSQLClient client) {
        this.client = client;
    }

    public Single<Category> find(long id) {
        String cacheKey = CATEGORY_ID.getKey() + id;
        return RedisVerticle
                .rxGet(cacheKey, Category.class)
                .flatMap(categoryCache -> {
                    if (categoryCache == null) {
                        String sql = "SELECT id, name, level, category_one AS categoryOne, category_two AS categoryTwo " +
                                "FROM category WHERE id = ? LIMIT 1";
                        return client
                                .rxQueryWithParams(sql, new JsonArray().add(id))
                                .flatMap(resultSet -> {
                                    List<JsonObject> rows = resultSet.getRows();
                                    Category categoryDb = rows.isEmpty() ? null : rows.get(0).mapTo(Category.class);
                                    if (categoryDb != null) {
                                        redisClient
                                                .rxSet(cacheKey, JsonObject.mapFrom(categoryDb).toString())
                                                .subscribe();
                                    }
                                    return new AsyncResultSingle<>(handler ->
                                            handler.handle(Future.succeededFuture(categoryDb))
                                    );
                                });
                    } else {
                        return Single.just(categoryCache);
                    }
                });

    }

    public Single<UpdateResult> insert(JsonObject categoryJson) {
        String sql = "INSERT category (name, level, category_one, category_two) values (?,?,?,?)";
        JsonArray params = new JsonArray()
                .add(categoryJson.getValue("name"))
                .add(categoryJson.getValue("level"))
                .add(categoryJson.getValue("categoryOne") == null ? 0 : categoryJson.getValue("categoryOne"))
                .add(categoryJson.getValue("categoryTwo") == null ? 0 : categoryJson.getValue("categoryTwo"));
        return client.rxUpdateWithParams(sql, params);
    }

    public Single<UpdateResult> update(JsonObject categoryJson) {
        String sql = "UPDATE category SET name = ?, level = ?, category_one = ?, category_two = ? WHERE id = ?";
        JsonArray params = new JsonArray()
                .add(categoryJson.getValue("name"))
                .add(categoryJson.getValue("level"))
                .add(categoryJson.getValue("categoryOne") == null ? 0 : categoryJson.getValue("categoryOne"))
                .add(categoryJson.getValue("categoryTwo") == null ? 0 : categoryJson.getValue("categoryTwo"))
                .add(categoryJson.getValue("id"));
        return client.rxUpdateWithParams(sql, params);
    }

    public Single<UpdateResult> delete(long id) {
        String sql = "DELETE FROM category WHERE id = ?";
        JsonArray params = new JsonArray().add(id);
        return client.rxUpdateWithParams(sql, params);
    }

}
