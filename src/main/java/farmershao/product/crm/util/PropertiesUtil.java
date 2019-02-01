package farmershao.product.crm.util;

import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 *
 * 配置文件工具类
 *
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 1:52
 */
public class PropertiesUtil {

    private final static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 读取配置文件（.json 文件）
     * @param resourceLocation  文件路径（resources下）
     * @return  JsonObject
     */
    public static JsonObject loadProperties(String resourceLocation) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(getConfigStream(resourceLocation))));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return new JsonObject(sb.toString());
        } catch (IOException e) {
            log.error("加载配置文件{}失败: {}", resourceLocation , e);
        }
        return null;
    }

    private static InputStream getConfigStream(String resourceLocation) {
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;
        if (ctxClsLoader != null) {
            is = ctxClsLoader.getResourceAsStream(resourceLocation);
        }
        return is;
    }
}
