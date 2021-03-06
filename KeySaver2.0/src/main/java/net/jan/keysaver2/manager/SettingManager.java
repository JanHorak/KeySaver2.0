/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jan.keysaver2.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Jan Horak
 */
public class SettingManager {

    private Properties property;
    private String filePathINI = "";

    public SettingManager(String path) {
        filePathINI = path;
    }

    public Properties initAndReturnProperties() throws IOException {
        initProperty();
        return this.property;
    }

    public String returnSetLanguage() throws IOException {
        String result = "";
        initProperty();
        result = property.getProperty("LANG");
        System.out.println("Language founded: " + result);
        return result;
    }

    public void saveLanguageInIniFile(String lang) throws FileNotFoundException, IOException {
        initProperty();
        property.setProperty("LANG", lang);
        property.store(new FileOutputStream(new File(filePathINI)), "");
        System.out.println("Saved Language: " + lang);
    }

    public void storeProperty(String key, String value) throws FileNotFoundException, IOException {
        initProperty();
        value = new String(value.getBytes(), Charset.forName("UTF-8"));
        property.setProperty(key, value);
        property.store(new FileOutputStream(new File(filePathINI)), "");
    }

    public String returnProperty(String propertyKey) throws IOException {
        initProperty();
        String tmp = property.getProperty(propertyKey);
        String result = new String(tmp.getBytes(), Charset.forName("UTF-8"));
        return result;
    }

    private void initProperty() throws IOException {
        property = new Properties();
        Reader r = new InputStreamReader(new FileInputStream(new File(filePathINI)), "UTF-8");
        property.load(r);
    }

    public List<Object> returnAllProperties() throws IOException {
        initProperty();
        List<Object> obList = new ArrayList<>();
        for (Object o : property.keySet()) {
            obList.add(o);
        }
        return obList;
    }

    public List<String> returnAllValues() throws IOException {
        initProperty();
        List<String> result = new ArrayList<>();
        List<Object> tmp = new ArrayList<>();
        tmp = returnAllProperties();

        for (Object o : tmp) {
            result.add(returnProperty((String) o));
        }
        return result;
    }
}
