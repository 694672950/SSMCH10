package cn.bdqn.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final ConfigManager configManager=new ConfigManager();
    private static Properties properties;

    public ConfigManager() {
        InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("database.properties");
        properties=new Properties();
        try {
           properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigManager getIntance(){
//            return Heler.CONFIG_MANAGER;
        return configManager;
    }

    public static String getValue(String key){
        return properties.getProperty(key);
    }

   /* private static class Heler{
        private static final ConfigManager CONFIG_MANAGER=new ConfigManager();
    }*/
}
