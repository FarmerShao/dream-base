package farmershao.product.console.common;

import io.vertx.core.json.JsonObject;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 3:50
 */
public enum RespEnum {

    SUCCESS(0, "操作成功"),
    SYSTEM_ERROR(500, "系统异常，请联系管理员"),
    PARAM_ERROR(400, "参数异常"),


    ACCOUNT_NOT_FOUND(10000, "账号不存在"),
    PASSWORD_ERROR(10001, "密码错误")
    ;

    private int code;
    private String msg;

    RespEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonObject convert2Json() {
        JsonObject rspJson = new JsonObject();
        rspJson.put("code", code);
        rspJson.put("msg", msg);
        return rspJson;
    }

    public String toJson(String msg) {
        JsonObject rspJson = new JsonObject();
        rspJson.put("code", code);
        rspJson.put("msg", msg);
        return rspJson.toString();
    }

    public String toJson() {
        return convert2Json().toString();
    }

    public String toJson(Object result) {
        JsonObject jsonObject = convert2Json();
        if (this == SUCCESS) {
            jsonObject.put("data", JsonObject.mapFrom(result));
        }
        return jsonObject.toString();
    }
}
