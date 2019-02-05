package farmershao.product.console.handler;

import farmershao.product.console.common.RespEnum;
import farmershao.product.console.dao.DatabaseMapperFactory;
import farmershao.product.console.dao.mapper.AdminManagerMapper;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.ext.auth.jwt.JWTAuth;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author ShaoYu
 * @since 2019/2/4 0004 下午 9:09
 */
public class JwtHandler {

    private final static Logger log = LoggerFactory.getLogger(JwtHandler.class);

    private JWTAuth auth;
    private String issuer;
    private String subject;


    private AdminManagerMapper adminManagerMapper = DatabaseMapperFactory.getMapper(AdminManagerMapper.class);

    public JwtHandler(JWTAuth auth, String issuer, String subject) {
        this.auth = auth;
        this.issuer = issuer;
        this.subject = subject;
    }

    public void auth(RoutingContext context) {
        HttpServerRequest request = context.request();
        String uri = request.uri();
        // 若非登录操作，则进行鉴权
        if (!(uri.equals("/loginPage.html") || uri.equals("/login"))) {
            // 获取鉴权 token
            String authorization = request.getHeader("Authorization");
            if (StringUtils.isBlank(authorization)) {   //没有鉴权信息，直接返回401
                context.fail(401);
                return;
            }
            // 因为鉴权的内容为 Bearer <token value> 所以要截取7个字符的长度
            String token = authorization.substring(7);
            // 判断用户是否有对应的权限
            auth
                    .rxAuthenticate(new JsonObject().put("jwt", token))
                    .subscribe(user -> {
                        JsonObject entries = user.principal();
                        context.fail(401);
                    });
        }
        context.next();
    }

    public void login(RoutingContext context) {
        HttpServerRequest request = context.request();
        String phone = request.getHeader("phone");
        String password = request.getHeader("password");
        //校验参数
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
            context.response().end(RespEnum.PARAM_ERROR.toJson());
            return;
        }
        adminManagerMapper
                .findByPhone(phone)
                .doOnError(exp -> context.response().end(RespEnum.SYSTEM_ERROR.toJson()))
                .subscribe(adminManager -> {
                    if (adminManager == null) {
                        context.response().end(RespEnum.ACCOUNT_NOT_FOUND.toJson());
                    } else if (!password.equals(adminManager.getPassword())) {
                        context.response().end(RespEnum.PASSWORD_ERROR.toJson());
                    } else {
                        // 若用户名&密码正确，则生成JwtToken，并存入响应的 data 字段中
                        String token = auth.generateToken(
                                new JsonObject().put("username", phone),
                                new JWTOptions()
                                        .setSubject(subject)
                                        .setIssuer(issuer)
                                        .setAlgorithm("RS256")
                                        .setExpiresInMinutes(60 * 2)
                                        // TODO 从数据库中获取用户的permissions
                                        .setPermissions(Arrays.asList("categoryGET"))
                        );
                        context.response().end(RespEnum.SUCCESS.toJson(new JsonObject().put("token", token)));
                    }
                });
    }

}
