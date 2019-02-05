package farmershao.product.console.verticle;

import farmershao.product.console.handler.AdminManagerHandler;
import farmershao.product.console.handler.CategoryHandler;
import farmershao.product.console.handler.JwtHandler;
import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.auth.jwt.JWTAuth;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.*;
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
    private JWTAuth auth;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("开始发布：HttpServerVerticle");
        JWTAuthOptions config =
                new JWTAuthOptions()
                        .addPubSecKey(
                                new PubSecKeyOptions()
                                        .setAlgorithm("RS256")
                                        .setPublicKey(
                                                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxPSbCQY5mBKFDIn1kggv\n" +
                                                        "Wb4ChjrctqD4nFnJOJk4mpuZ/u3h2ZgeKJJkJv8+5oFO6vsEwF7/TqKXp0XDp6IH\n" +
                                                        "byaOSWdkl535rCYR5AxDSjwnuSXsSp54pvB+fEEFDPFF81GHixepIbqXCB+BnCTg\n" +
                                                        "N65BqwNn/1Vgqv6+H3nweNlbTv8e/scEgbg6ZYcsnBBB9kYLp69FSwNWpvPmd60e\n" +
                                                        "3DWyIo3WCUmKlQgjHL4PHLKYwwKgOHG/aNl4hN4/wqTixCAHe6KdLnehLn71x+Z0\n" +
                                                        "SyXbWooftefpJP1wMbwlCpH3ikBzVIfHKLWT9QIOVoRgchPU3WAsZv/ePgl5i8Co\n" +
                                                        "qwIDAQAB")
                                        .setSecretKey(
                                                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDE9JsJBjmYEoUM\n" +
                                                        "ifWSCC9ZvgKGOty2oPicWck4mTiam5n+7eHZmB4okmQm/z7mgU7q+wTAXv9Oopen\n" +
                                                        "RcOnogdvJo5JZ2SXnfmsJhHkDENKPCe5JexKnnim8H58QQUM8UXzUYeLF6khupcI\n" +
                                                        "H4GcJOA3rkGrA2f/VWCq/r4fefB42VtO/x7+xwSBuDplhyycEEH2Rgunr0VLA1am\n" +
                                                        "8+Z3rR7cNbIijdYJSYqVCCMcvg8cspjDAqA4cb9o2XiE3j/CpOLEIAd7op0ud6Eu\n" +
                                                        "fvXH5nRLJdtaih+15+kk/XAxvCUKkfeKQHNUh8cotZP1Ag5WhGByE9TdYCxm/94+\n" +
                                                        "CXmLwKirAgMBAAECggEAeQ+M+BgOcK35gAKQoklLqZLEhHNL1SnOhnQd3h84DrhU\n" +
                                                        "CMF5UEFTUEbjLqE3rYGP25mdiw0ZSuFf7B5SrAhJH4YIcZAO4a7ll23zE0SCW+/r\n" +
                                                        "zr9DpX4Q1TP/2yowC4uGHpBfixxpBmVljkWnai20cCU5Ef/O/cAh4hkhDcHrEKwb\n" +
                                                        "m9nymKQt06YnvpCMKoHDdqzfB3eByoAKuGxo/sbi5LDpWalCabcg7w+WKIEU1PHb\n" +
                                                        "Qi+RiDf3TzbQ6TYhAEH2rKM9JHbp02TO/r3QOoqHMITW6FKYvfiVFN+voS5zzAO3\n" +
                                                        "c5X4I+ICNzm+mnt8wElV1B6nO2hFg2PE9uVnlgB2GQKBgQD8xkjNhERaT7f78gBl\n" +
                                                        "ch15DRDH0m1rz84PKRznoPrSEY/HlWddlGkn0sTnbVYKXVTvNytKSmznRZ7fSTJB\n" +
                                                        "2IhQV7+I0jeb7pyLllF5PdSQqKTk6oCeL8h8eDPN7awZ731zff1AGgJ3DJXlRTh/\n" +
                                                        "O6zj9nI8llvGzP30274I2/+cdwKBgQDHd/twbiHZZTDexYewP0ufQDtZP1Nk54fj\n" +
                                                        "EpkEuoTdEPymRoq7xo+Lqj5ewhAtVKQuz6aH4BeEtSCHhxy8OFLDBdoGCEd/WBpD\n" +
                                                        "f+82sfmGk+FxLyYkLxHCxsZdOb93zkUXPCoCrvNRaUFO1qq5Dk8eftGCdC3iETHE\n" +
                                                        "6h5avxHGbQKBgQCLHQVMNhL4MQ9slU8qhZc627n0fxbBUuhw54uE3s+rdQbQLKVq\n" +
                                                        "lxcYV6MOStojciIgVRh6FmPBFEvPTxVdr7G1pdU/k5IPO07kc6H7O9AUnPvDEFwg\n" +
                                                        "suN/vRelqbwhufAs85XBBY99vWtxdpsVSt5nx2YvegCgdIj/jUAU2B7hGQKBgEgV\n" +
                                                        "sCRdaJYr35FiSTsEZMvUZp5GKFka4xzIp8vxq/pIHUXp0FEz3MRYbdnIwBfhssPH\n" +
                                                        "/yKzdUxcOLlBtry+jgo0nyn26/+1Uyh5n3VgtBBSePJyW5JQAFcnhqBCMlOVk5pl\n" +
                                                        "/7igiQYux486PNBLv4QByK0gV0SPejDzeqzIyB+xAoGAe5if7DAAKhH0r2M8vTkm\n" +
                                                        "JvbCFjwuvhjuI+A8AuS8zw634BHne2a1Fkvc8c3d9VDbqsHCtv2tVkxkKXPjVvtB\n" +
                                                        "DtzuwUbp6ebF+jOfPK0LDuJoTdTdiNjIcXJ7iTTI3cXUnUNWWphYnFogzPFq9CyL\n" +
                                                        "0fPinYmDJpkwMYHqQaLGQyg=")
                        );
        auth = JWTAuth.create(vertx, config);

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

        filter(router);

        //=== Admin Manager ===
        AdminManagerHandler adminManagerHandler = new AdminManagerHandler();
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

    private void filter(Router router) {
        JwtHandler jwtHandler = new JwtHandler(auth, config().getString("issuer"), config().getString("subject"));
        // 非静态资源路由
        router.routeWithRegex("^((?!frontResources).)*$")
                .handler(CookieHandler.create())
                // 请求日志(过滤静态文件的路径)
                .handler(LoggerHandler.create(LoggerFormat.SHORT))
                // 鉴权
                .handler(jwtHandler::auth);

        // 处理静态资源
        router.route("/frontResources/*").handler(StaticHandler.create("/frontResources"));
        // 路由以.html 结尾的页面
        router
                .routeWithRegex("^(.*\\.html)$")
                .handler(context -> {
                    context.put("domain", "http://" + host + ":" + port);
                    context.next();
                });
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

        router.get("/loginPage.html").handler(this::loginPage);
        router.get("/index.html").handler(this::index);

        router.post("/login").handler(jwtHandler::login);
    }

    private void loginPage(RoutingContext context) {
        context.response().putHeader("Content-Type", "text/html");
        templateEngine
                .rxRender(context, "views", "/login.ftl")
                .subscribe(buffer -> context.response().end(buffer));
    }

    private void index(RoutingContext context) {
        context.response().putHeader("Content-Type", "text/html");
        templateEngine
                .rxRender(context, "views", "/index.ftl")
                .subscribe(buffer -> context.response().end(buffer));
    }


}
