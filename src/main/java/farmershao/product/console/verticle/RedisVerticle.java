package farmershao.product.console.verticle;

import farmershao.product.console.util.PropertiesUtil;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.impl.AsyncResultSingle;
import io.vertx.reactivex.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 7:37
 */
public class RedisVerticle extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(RedisVerticle.class);

    public static RedisClient redisClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("开始发布: RedisVerticle");
        redisClient = RedisClient.create(vertx, new RedisOptions(config()));
        startFuture.complete();
        log.info("发布成功：RedisVerticle");
    }

    public static <T> Single<T> rxGet(String key, Class<T> tClass) {
        return redisClient
                .rxGet(key)
                .flatMap(str ->
                    new AsyncResultSingle<>(handler ->
                        handler.handle(Future.succeededFuture(StringUtils.isBlank(str) ? null : new JsonObject(str).mapTo(tClass)))
                    )
                );
    }

}
