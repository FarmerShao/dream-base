package farmershao.product.console.verticle;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.auth.AuthProvider;
import io.vertx.reactivex.ext.auth.User;
import io.vertx.reactivex.ext.auth.shiro.ShiroAuth;


/**
 * @author ShaoYu
 * @since 2019/2/4 0004 下午 8:33
 */
public class ShiroVerticle extends AbstractVerticle {

    public static final String SHRIO_ADDRESS = "shiro.auth";


    private static AuthProvider authProvider;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        JsonObject config = new JsonObject().put("properties_path", "classpath:shiro-auth.properties");
        authProvider = ShiroAuth.create(vertx, new ShiroAuthOptions().setType(ShiroAuthRealmType.PROPERTIES).setConfig(config));
    }

    public static Single<User> rxAuthenticate(JsonObject user) {
        return authProvider.rxAuthenticate(user);
    }

    public static void authenticate(JsonObject user, Handler<AsyncResult<User>> handler) {
        authProvider.authenticate(user, handler);
    }

}
