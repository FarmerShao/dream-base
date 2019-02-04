package farmershao.product.console.handler;

import farmershao.product.console.common.RespEnum;
import farmershao.product.console.dao.DatabaseMapperFactory;
import farmershao.product.console.dao.mapper.CategoryMapper;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShaoYu
 * @since 2019/2/4 0004 下午 12:13
 */
public class CategoryHandler {

    private final static Logger log = LoggerFactory.getLogger(CategoryHandler.class);

    private CategoryMapper categoryMapper = DatabaseMapperFactory.getMapper(CategoryMapper.class);

    public void find(RoutingContext context) {
        try {
            categoryMapper
                    .find(Integer.parseInt(context.pathParam("id")))
                    .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                    .subscribe(category -> context.response().end(RespEnum.SUCCESS.toJson(category)));
        } catch (Exception e) {
            log.error("Method [find] error:", e);
            context.response().end(RespEnum.SYSTEM_ERROR.toJson());
        }
    }

    public void insert(RoutingContext context) {
        try {
            categoryMapper
                    .insert(context.getBodyAsJson())
                    .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                    .subscribe(updateResult -> context.response().end(RespEnum.SUCCESS.toJson()));
        } catch (Exception e) {
            log.error("Method [find] error:", e);
            context.response().end(RespEnum.SYSTEM_ERROR.toJson());
        }
    }

    public void update(RoutingContext context) {
        try {
            categoryMapper
                    .update(context.getBodyAsJson().put("id", Long.parseLong(context.pathParam("id"))))
                    .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                    .subscribe(updateResult -> context.response().end(RespEnum.SUCCESS.toJson()));
        } catch (Exception e) {
            log.error("Method [find] error:", e);
            context.response().end(RespEnum.SYSTEM_ERROR.toJson());
        }
    }

    public void delete(RoutingContext context) {
        try {
            categoryMapper
                    .delete(Long.parseLong(context.pathParam("id")))
                    .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                    .subscribe(updateResult -> context.response().end(RespEnum.SUCCESS.toJson()));
        } catch (Exception e) {
            log.error("Method [find] error:", e);
            context.response().end(RespEnum.SYSTEM_ERROR.toJson());
        }
    }
}
