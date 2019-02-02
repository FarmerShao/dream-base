package farmershao.product.console.handler;

import farmershao.product.console.common.RespEnum;
import farmershao.product.console.dao.DatabaseMapperFactory;
import farmershao.product.console.dao.mapper.AdminManagerMapper;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
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
        context.response().putHeader("Content-Type", "application/json");
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

    public void login(RoutingContext context) {
        HttpServerRequest request = context.request();
        String phone = request.getParam("phone");
        String password = request.getParam("password");
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
            context.response().end(RespEnum.PARAM_ERROR.toJson());
            return;
        }
        context.response().putHeader("Content-Type", "application/json");
        adminManagerMapper
                .findByPhone(phone)
                .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                .subscribe(adminManager -> {
                    if (adminManager == null) {
                        context.response().end(RespEnum.ACCOUNT_NOT_FOUND.toJson());
                    } else if (!password.equals(adminManager.getPassword())){
                        context.response().end(RespEnum.PASSWORD_ERROR.toJson(adminManager));
                    } else {
                        context.response().end(RespEnum.SUCCESS.toJson());
                    }
                });
    }

}
