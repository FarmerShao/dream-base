package farmershao.product.console;

import io.vertx.core.VertxOptions;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 3:34
 */
public class Launcher extends io.vertx.core.Launcher {

    public static void main(String[] args) {
        new Launcher().dispatch(args);
    }

    @Override
    public void beforeStartingVertx(VertxOptions options) {
        options.setClustered(false);
//                .setClusterHost("127.0.0.1")
//                .setClusterManager(new ZookeeperClusterManager());
    }

}

