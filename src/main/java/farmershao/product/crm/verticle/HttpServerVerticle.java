package farmershao.product.crm.verticle;

import farmershao.product.crm.handler.AdminManagerHandler;
import farmershao.product.crm.util.PropertiesUtil;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 1:56
 */
public class HttpServerVerticle extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(HttpServerVerticle.class);

    private String host;
    private int port;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        host = config().getString("http.host","127.0.0.1");
        port = config().getInteger("http.port", 8089);

        configTheHttpSever()
                .subscribe(server -> {
                    log.info("HttpServer stared, host:[{}]  port:[{}]", host, port);
                    startFuture.complete();
                });
    }

    private Single<HttpServer> configTheHttpSever() {
        Router router = Router.router(vertx);
        router.route()
                .handler(LoggerHandler.create(LoggerFormat.SHORT))
                .handler(context -> {
                    context.response().putHeader("Content-Type", "application/json");
                    context.next();
                });

        router.post().handler(BodyHandler.create());
        router.put().handler(BodyHandler.create());

        AdminManagerHandler adminManagerHandler = new AdminManagerHandler();
        router.get("/manager/:id").handler(adminManagerHandler::find);

        HttpServer httpServer = vertx.createHttpServer().requestHandler(router::accept);
        return httpServer.rxListen(port, host);
    }

}
