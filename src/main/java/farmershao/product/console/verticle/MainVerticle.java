package farmershao.product.console.verticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动类
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 1:56
 */
public class MainVerticle extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.debug("开始发布MainVerticle");
        vertx
                .rxDeployVerticle("farmershao.product.console.verticle.RedisVerticle")
                .flatMap(id ->
                    vertx.rxDeployVerticle("farmershao.product.console.verticle.DatabaseVerticle"))
                .flatMap(id ->
                    vertx.rxDeployVerticle("farmershao.product.console.verticle.HttpServerVerticle", new DeploymentOptions().setInstances(2)))
                .subscribe(id -> startFuture.complete(), startFuture::fail);
    }
}
