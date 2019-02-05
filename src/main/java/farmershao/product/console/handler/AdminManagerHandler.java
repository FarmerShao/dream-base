package farmershao.product.console.handler;

import farmershao.product.console.common.RespEnum;
import farmershao.product.console.dao.DatabaseMapperFactory;
import farmershao.product.console.dao.mapper.AdminManagerMapper;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 3:43
 */
public class AdminManagerHandler {

    private final static Logger log = LoggerFactory.getLogger(AdminManagerHandler.class);

    private AdminManagerMapper adminManagerMapper = DatabaseMapperFactory.getMapper(AdminManagerMapper.class);

    public AdminManagerHandler() { }

    public void find(RoutingContext context) {
        try {
            adminManagerMapper
                    .find(Integer.parseInt(context.pathParam("id")))
                    .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                    .subscribe(adminManager -> {
                        context.response().end(RespEnum.SUCCESS.toJson(adminManager));
                    });
        } catch (Exception e) {
            log.error("Method [find] error:", e);
            context.response().end(RespEnum.SYSTEM_ERROR.toJson());
        }
    }



}
