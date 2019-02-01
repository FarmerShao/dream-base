package farmershao.product.crm.verticle;

import farmershao.product.crm.dao.DatabaseMapperFactory;
import farmershao.product.crm.util.PropertiesUtil;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.asyncsql.AsyncSQLClient;
import io.vertx.reactivex.ext.asyncsql.MySQLClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 2:13
 */
public class DatabaseVerticle extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(DatabaseVerticle.class);

    private AsyncSQLClient client;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("开始发布DatabaseVerticle");
        JsonObject sqlConfig = PropertiesUtil.loadProperties("mysql-config.json");
        client = MySQLClient.createShared(vertx, sqlConfig);

        // 连接到数据库，检测是否连接正常
        client
                .rxGetConnection()
                .subscribe(sqlConnection -> {
                    DatabaseMapperFactory.init(client);
                    startFuture.complete();
                    log.info("发布成功：DatabaseVerticle");
                });

    }
}
