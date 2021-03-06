package farmershao.product.console.util;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

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
    public static JsonObject loadClassPath(String resourceLocation) {
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

    /**
     * 根据文件路径读取配置文件，并转为 JsonObject
     *
     * @param filePath  文件路径
     * @return          JsonObject
     */
    public static JsonObject loadFilePath(String filePath) {
        File config = new File(filePath);
        JsonObject conf = new JsonObject();
        if (config.isFile()) {
            if (log.isDebugEnabled()) {
                log.debug("Reading config file: {}", config.getAbsolutePath());
            }
            try (Scanner scanner = new Scanner(config).useDelimiter("\\A")) {
                String sconf = scanner.next();
                try {
                    conf = new JsonObject(sconf);
                } catch (DecodeException e) {
                    log.error("Configuration file {} does not contain a valid JSON object", sconf);
                }
            } catch (FileNotFoundException e) {
                // Ignore it.
            }
        } else {
            log.error("Config file not found {}", config.getAbsolutePath());
        }
        return conf;
    }
}
