package farmershao.product.crm;

import farmershao.product.crm.util.PropertiesUtil;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

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

    @Override
    public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
        super.beforeDeployingVerticle(deploymentOptions);

        if (deploymentOptions.getConfig() == null) {
            deploymentOptions.setConfig(new JsonObject());
        }
        deploymentOptions.getConfig().mergeIn(PropertiesUtil.loadProperties("application-config.json"));
    }


}

