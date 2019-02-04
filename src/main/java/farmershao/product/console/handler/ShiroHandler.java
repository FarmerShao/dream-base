package farmershao.product.console.handler;

import farmershao.product.console.common.RespEnum;
import farmershao.product.console.verticle.RedisVerticle;
import farmershao.product.console.verticle.ShiroVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.core.impl.AsyncResultSingle;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ShaoYu
 * @since 2019/2/4 0004 下午 9:09
 */
public class ShiroHandler {

    public void auth(RoutingContext context) {
        HttpServerRequest request = context.request();
        String uri = request.uri();
        // 若非登录操作，则进行鉴权
        if (!(uri.equals("/loginPage.html") || uri.equals("/login"))) {
            //1. 获取token
            String token = request.getHeader("token");
            if (StringUtils.isBlank(token)) {
                context.response().putHeader("Content-Type", "application/json");
                context.response().end(RespEnum.NO_AUTH.toJson());
                return;
            }
            RedisVerticle
                    .rxGet(token, JsonObject.class)
                    .flatMap(userJson -> {
                        if (userJson == null) {
                            return new AsyncResultSingle<>(handler -> handler.handle(Future.succeededFuture(null)));
                        }
                        return ShiroVerticle.rxAuthenticate(userJson);
                    })
                    .subscribe(user -> {
                        if (user == null) {
                            context.response().putHeader("Content-Type", "application/json");
                            context.response().end(RespEnum.NO_AUTH.toJson());
                        }
                    });
        } else if (uri.equals("/login")){
            String phone = request.getParam("phone");
            String password = request.getParam("password");



        } else {
            context.next();
        }
    }
}
