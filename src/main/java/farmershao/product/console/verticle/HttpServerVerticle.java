package farmershao.product.console.verticle;

import farmershao.product.console.handler.AdminManagerHandler;
import farmershao.product.console.handler.CategoryHandler;
import farmershao.product.console.util.PropertiesUtil;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.*;
import io.vertx.reactivex.ext.web.sstore.ClusteredSessionStore;
import io.vertx.reactivex.ext.web.sstore.LocalSessionStore;
import io.vertx.reactivex.ext.web.sstore.SessionStore;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 1:56
 */
public class HttpServerVerticle extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(HttpServerVerticle.class);

    private FreeMarkerTemplateEngine templateEngine;

    private String host;
    private int port;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("开始发布：HttpServerVerticle");
        host = config().getString("host");
        port = config().getInteger("port");
        templateEngine = FreeMarkerTemplateEngine.create();
        configTheHttpSever()
                .subscribe(server -> {
                    log.info("HttpServer stared, host:[{}]  port:[{}]", host, port);
                    startFuture.complete();
                });
    }

    private Single<HttpServer> configTheHttpSever() {
        Router router = Router.router(vertx);

        // 使用session之前需配置CookieHandler
        router.route().handler(CookieHandler.create());
        // 使用本地存储Session,若需换成集群存储，可以使用 ClusteredSessionStore
        SessionStore store = LocalSessionStore.create(vertx, "sessionMap");
        SessionHandler sessionHandler = SessionHandler.create(store);
        router.route().handler(sessionHandler);

        router
                .post()
                .handler(BodyHandler.create())
                .handler(context -> {
                    context.response().putHeader("Content-Type", "application/json");
                    context.next();
                });
        router
                .put()
                .handler(BodyHandler.create())
                .handler(context -> {
                    context.response().putHeader("Content-Type", "application/json");
                    context.next();
                });
        router
                .delete()
                .handler(context -> {
                    context.response().putHeader("Content-Type", "application/json");
                    context.next();
                });

        // 处理静态资源
        router.route("/frontResources/*").handler(StaticHandler.create("/frontResources"));
        // 请求日志(过滤静态文件的路径)
        router.route().pathRegex("^((?!frontResources).)*$").handler(LoggerHandler.create(LoggerFormat.SHORT));

        router.get("/loginPage").handler(this::loginPage);
        router.get("/index").handler(this::index);

        //=== Admin Manager ===
        AdminManagerHandler adminManagerHandler = new AdminManagerHandler();
        router.post("/login").handler(adminManagerHandler::login);
        router.get("/manager/:id").handler(adminManagerHandler::find);

        //=== category ===
        CategoryHandler categoryHandler = new CategoryHandler();
        router.get("/category/:id").handler(categoryHandler::find);
        router.post("/category").handler(categoryHandler::insert);
        router.put("/category/:id").handler(categoryHandler::update);
        router.delete("/category/:id").handler(categoryHandler::delete);

        HttpServer httpServer = vertx.createHttpServer().requestHandler(router::accept);
        return httpServer.rxListen(port, host);
    }

    public void loginPage(RoutingContext context) {
        context.response().putHeader("Content-Type", "text/html");
        templateEngine
                .rxRender(context, "views", "/login.ftl")
                .subscribe( buffer -> context.response().end(buffer));
    }

    public void index(RoutingContext context) {
        context.response().putHeader("Content-Type", "text/html");
        templateEngine
                .rxRender(context, "views", "/index.ftl")
                .subscribe( buffer -> context.response().end(buffer));
    }



}
