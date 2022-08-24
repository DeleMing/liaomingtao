package org.lmt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.neo4j.cypher.internal.compiler.v3_1.codegen.ir.expressions.Division;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.tools.tree.AndExpression;
import sun.tools.tree.OrExpression;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 工具类
 *
 * @author zhuzhigang (<a href="mailto:zhuzhigang@zork.com.cn">zhuzhigang@zork.com.cn</a>)
 * @date 2020-02-21
 */
public class ToolUtils {
    public static final int INT100 = 100;
    public static final String UTF_8 = "UTF-8";
    public static final String REGEX = "[\\s*\t\n\r]";
    public static final String STRING = "";
    public static final String LIST = "list";
    public static final String OS_NAME = "os.name";
    public static final String LINUX1 = "linux";
    public static final String MAC = "mac";
    public static final String G = "g";
    public static final String C = "c";
    public static final String STR = ":";
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolUtils.class);
    private final static String ENCODE = UTF_8;
    private static String EMPTYSTR = STRING;
    private static String NULLSTR = null;
    private static String CODE = "code";
    private static String CODESUCCESS = "000000";
    private static String CODEERROR = "000999";
    private static String DATA = "data";
    private static String MESSAGE = "message";
    private static String LINUX = LINUX1;
    private static String WINDOWS = "windows";
    public static final String BACKSLASH = "\\";
    public static final String LOGSTASH = "logstash";

    /**
     * 时间戳转日期
     *
     * @param timestamp
     * @return
     */
    public static String timestampToData(String timestamp) {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date(Long.parseLong(timestamp)));
        return date;
    }

    /**
     * 时间戳转指定日期格式日期
     *
     * @param timestamp
     * @return
     */
    public static String timestampToData(long timestamp, String dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        String date = sdf.format(new Date(timestamp));
        return date;
    }

    /**
     * 指定日期格式日期转时间戳
     *
     * @param
     * @return
     */
    public static long dataToTimestamp(String dateStr, String dateType) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        Date d = sdf.parse(dateStr);
        return d.getTime();
    }

    /**
     * 获取当前时间并转换成yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getNowTime() {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取当前时间并转换成yyyy-MM-dd HH:mm:ss.SSS
     */
    public static long getNowTimestamp() {
        Date date = new Date();
        long timestamp = date.getTime();
        return timestamp;
    }

    /**
     * 指定时间并转换成yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Date stringToData(String date) throws ParseException {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = sdf.parse(date);
        return d;
    }

    /**
     * 指定时间并转换成yyyy-MM-dd HH:mm:ss.SSS
     */
    public static long stringToTimestamp(String date) throws ParseException {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = sdf.parse(date);
        return d.getTime();
    }

    /**
     * 指定时间并转换成yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String dataToString(Date date) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 执行结果状态值变换
     *
     * @param statusCode
     * @return
     */
    public static String judgeJobStatus(int statusCode) {
        String status = "未知错误";
        switch (statusCode) {
            case 1:
                status = "未执行";
                break;
            case 2:
                status = "正在执行";
                break;
            case 3:
                status = "执行成功";
                break;
            case 4:
                status = "执行失败";
                break;
            case 5:
                status = "跳过";
                break;
            case 6:
                status = "忽略错误";
                break;
            case 7:
                status = "等待用户";
                break;
            case 8:
                status = "手动结束";
                break;
            case 9:
                status = "状态异常";
                break;
            case 10:
                status = "步骤强制终止中";
                break;
            case 11:
                status = "步骤强制终止成功";
                break;
            case 12:
                status = "步骤强制终止失败";
                break;
            default:
                status = "未知错误";
                break;
        }
        return status;
    }

    /**
     * 执行操作机器状态
     *
     * @param statusCode
     * @return
     */
    public static String judgeIpStatus(int statusCode) {
        String ipStaus = "未知错误";
        switch (statusCode) {
            case 1:
                ipStaus = "Agent异常";
                break;
            case 3:
                ipStaus = "上次已成功";
                break;
            case 5:
                ipStaus = "等待执行";
                break;
            case 7:
                ipStaus = "正在执行";
                break;
            case 9:
                ipStaus = "执行成功";
                break;
            case 11:
                ipStaus = "任务失败";
                break;
            case 12:
                ipStaus = "任务下发失败";
                break;
            case 13:
                ipStaus = "任务超时";
                break;
            case 15:
                ipStaus = "任务日志错误";
                break;
            case 101:
                ipStaus = "脚本执行失败";
                break;
            case 102:
                ipStaus = "脚本执行超时";
                break;
            case 103:
                ipStaus = "脚本执行被终止";
                break;
            case 104:
                ipStaus = "脚本返回码非零";
                break;
            case 202:
                ipStaus = "文件传输失败";
                break;
            case 203:
                ipStaus = "源文件不存在";
                break;
            case 310:
                ipStaus = "Agent异常";
                break;
            case 311:
                ipStaus = "用户名不存在";
                break;
            case 320:
                ipStaus = "文件获取失败";
                break;
            case 321:
                ipStaus = "文件超出限制";
                break;
            case 329:
                ipStaus = "文件传输错误";
                break;
            case 399:
                ipStaus = "任务执行出错";
                break;
            default:
                ipStaus = "未知错误";
                break;
        }
        return ipStaus;
    }

    /**
     * InputStream转换为Base64
     *
     * @param in
     * @return
     */
    public static String getBase64FromInputStream(InputStream in) throws IOException {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[INT100];
        int rc = 0;
        while ((rc = in.read(buff, 0, INT100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        data = swapStream.toByteArray();
        return new String(Base64.encodeBase64(data));
    }

    public static InputStream readFileToInputStream(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } else {
            LOGGER.error(filePath + "文件不存在");
            return null;
        }
    }

    /**
     * 编码转换Base64
     *
     * @param content 内容
     * @return base64字符串
     * @throws IOException
     */
    public static String changeToBase64(String content) {
        byte[] result = Base64.encodeBase64(content.getBytes(StandardCharsets.UTF_8));

        String encodedText = Base64.encodeBase64String(result).replaceAll(REGEX, STRING);
        String str = new String(Base64.decodeBase64(encodedText), StandardCharsets.UTF_8);
        return str;
    }

    /**
     * base64解码
     *
     * @param content
     * @return
     */
    public static String changeBase64ToString(String content) {
        if (content == null) {
            return null;
        }
        try {
            byte[] b = Base64.decodeBase64(content);
            return new String(b, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断字符串是否为base64编码
     *
     * @param str 需要判断是否为base64的字符串
     * @return true: 是base64编码 false: 不是base64编码
     */
    public static boolean hasBase64(String str) {

        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return str.matches(base64Pattern);
    }

    /**
     * 创建文件夹
     */
    public static boolean creatFileDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                LOGGER.error("creatFileDir errMsg:{}", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 本地生成配置文件
     */
    public static boolean createFileConfig(String sourcePath, String typePath, String identifyingPath, String fileName, String fileContent) {
        StringBuffer paths = new StringBuffer();
        String slash = judgeFileSlash();
        paths.append(sourcePath);
        boolean sourcePathTf = creatFileDir(paths.toString());
        if (sourcePathTf) {
            paths.append(slash);
            paths.append(typePath);
            boolean typePathTf = creatFileDir(paths.toString());
            if (typePathTf) {
                paths.append(slash);
                paths.append(identifyingPath);
                boolean identifyingPathTf = creatFileDir(paths.toString());
                if (identifyingPathTf) {
                    paths.append(slash);
                    paths.append(fileName);
                    File resultFile = new File(paths.toString());
                    if (!resultFile.exists()) {
                        try {
                            boolean createSuccess = resultFile.createNewFile();
                            if(createSuccess) {
                                execChmod(resultFile.getPath());
                            }else{
                                LOGGER.error("创建文件失败，文件名称为{}", resultFile.getName());
                            }
                        } catch (Exception e) {
                            LOGGER.error("createFileConfig errMsg:{}", e);
                            return false;
                        }
                    }
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(resultFile);
                        fw.write(fileContent);
                        fw.close();
                    } catch (Throwable e) {
                        LOGGER.error("errMsg:{}", e);
                        return false;
                    } finally {
                        if (fw != null) {
                            try {
                                fw.close();
                            } catch (Throwable e) {

                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 对于linux创建文件进行赋权
     *
     * @param filePath
     * @throws IOException
     */
    public static void execChmod(String filePath) throws IOException {
        String os = System.getProperty(OS_NAME);
        //拼接完整连接
        if (!os.toLowerCase().contains(WINDOWS)) {
            Runtime.getRuntime().exec("chmod 555 -R " + filePath);
        }
    }


    public static String judgeFileSlash() {
        String slash = "/";
        Properties properties = System.getProperties();
        String os = properties.getProperty(OS_NAME);
        if (os != null && os.toLowerCase().indexOf(LINUX1) > -1) {
            slash = "/";
        } else if (os != null && os.toLowerCase().indexOf(MAC) > -1) {
            slash = "/";
        } else {
            slash = "\\";
        }
        return slash;
    }

    public static String judgeLindFeed() {
        String lindFeed = "/";
        Properties properties = System.getProperties();
        String os = properties.getProperty(OS_NAME);
        if (os != null && os.toLowerCase().indexOf(LINUX1) > -1) {
            lindFeed = "\n";
        } else if (os != null && os.toLowerCase().indexOf(MAC) > -1) {
            lindFeed = "\n";
        } else {
            lindFeed = "\r\n";
        }
        return lindFeed;
    }

    public static Map<String, String> splitMappingByChar(String strMapping, String parentChar, String sonChar) {
        Map<String, String> resultMap = new HashMap<String, String>(100);
        if (strMapping.contains(parentChar)) {
            String[] jdkVersions = strMapping.split(parentChar);
            for (String jdk : jdkVersions) {
                String[] j = jdk.split(sonChar);
                resultMap.put(j[0].trim(), j[1].trim());
            }
        } else {
            String[] j = strMapping.split(sonChar);
            resultMap.put(j[0].trim(), j[1].trim());
        }
        return resultMap;
    }

    public static Map<String, List<String>> splitMappingByChar(String strMapping, String parentChar, String sonChar, String sonSonChar) {
        Map<String, List<String>> resultMap = new HashMap<String, List<String>>(100);
        if (strMapping.contains(parentChar)) {
            String[] strVersions = strMapping.split(parentChar);
            for (String str : strVersions) {
                String[] j = str.split(sonChar);
                String key = j[0].trim();
                String value = j[1].trim();
                List<String> valueList = splitListByChar(value, sonSonChar);
                resultMap.put(key, valueList);
            }
        } else {
            String[] j = strMapping.split(sonChar);
            String value = j[1].trim();
            List<String> valueList = splitListByChar(value, sonSonChar);
            resultMap.put(j[0].trim(), valueList);
        }
        return resultMap;
    }

    public static List<String> splitListByChar(String strMapping, String charStr) {
        List<String> resultList = new ArrayList<>();
        if (strMapping.contains(charStr)) {
            String[] jdkVersions = strMapping.split(charStr);
            for (String jdk : jdkVersions) {
                resultList.add(jdk);
            }
        } else {
            resultList.add(strMapping);
        }
        return resultList;
    }

    /**
     * 去除重复元素
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        if (list.size() > 0) {
            //list是有序可重复的，set是无序不可重复的
            //将list的元素转为set，可以将重复的元素去除，然后将去除后的数组还给list
            HashSet h = new HashSet<>(list);
            list.clear();
            list.addAll(h);
            //去除list中的"null"元素
            list.remove(null);
            //去除list中的空字符串元素
            list.remove(STRING);
        }
        return list;
    }

    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
           LOGGER.error("convertStreamToString errMsg:{}", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.error("errMsg:{}", e);
            }
        }
        return sb.toString();
    }

    public static String getUrlContent(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
        HttpEntity entity = httpResponse.getEntity();
        String content = STRING;
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            content = convertStreamToString(inputStream);
            inputStream.close();
        }
        httpClient.getConnectionManager().shutdown();
        return content;
    }


    public static boolean httpPost(String url, String strParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding(UTF_8);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            //请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = STRING;
                try {
                    //读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    //把json字符串转换成json对象
                } catch (Exception e) {
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        } finally {
            httpPost.releaseConnection();
            closeHttpClient(httpClient);
        }
        return true;
    }

    private static void closeHttpClient(CloseableHttpClient httpClient){
        try {
            if(null != httpClient){
                httpClient.close();
            }
        }catch (IOException e){
            LOGGER.error("errMsg :{}", e);
        }finally {

        }
    }

    /**
     * 转换存储单位为MB
     */
    public static Integer changeUnitOfStorageToMb(String memoryStr) {
        memoryStr = memoryStr.toLowerCase();
        int memoryMb = 0;
        if (memoryStr.contains(G)) {
            memoryMb = Integer.parseInt(memoryStr.substring(0, memoryStr.length() - 1)) * 1024;
        } else {
            memoryMb = Integer.getInteger(memoryStr);
        }
        return memoryMb;
    }

    /**
     * 转换存储单位为MB
     */
    public static Integer changeCore(String coreStr) {
        coreStr = coreStr.toLowerCase();
        int coreNum = 0;
        if (coreStr.contains(C)) {
            coreNum = Integer.parseInt(coreStr.substring(0, coreStr.length() - 1)) * 1024;
        } else {
            coreNum = Integer.getInteger(coreStr);
        }
        return coreNum;
    }

    public static String getUrlDecoderString(String str) {
        String result = STRING;
        if (null == str) {
            return STRING;
        }
        try {
            result = URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("errMsg:{}", e);
        }
        return result;
    }

    public static String getUrlEncoderString(String str) {
        String result = STRING;
        if (null == str) {
            return STRING;
        }
        try {
            result = URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("urlEncoder errMsg:{}", e);
        }
        return result;
    }

    public static String countCharcterPitch(int topClass) {
        StringBuilder charcterPitch = new StringBuilder();
        charcterPitch.append("    ");
        if (topClass > 0) {
            for (int i = 0; i < topClass; i++) {
                charcterPitch.append("    ");
            }
            return charcterPitch.toString();
        } else {
            return charcterPitch.toString();
        }
    }


    public static String zabbixUrl(String ip, String port, String zabbixServer, String methodAddress) {
        StringBuffer urlStr = new StringBuffer();
        urlStr.append("http://");
        urlStr.append(ip);
        urlStr.append(STR);
        urlStr.append(port);
        urlStr.append(zabbixServer);
        urlStr.append(methodAddress);
        return urlStr.toString();
    }

    public static String hostInfoContent(String ip, String port) {
        StringBuffer hostInfoStr = new StringBuffer();
        hostInfoStr.append("\"hostInfo\":{\"ip\":\"");
        hostInfoStr.append(ip);
        hostInfoStr.append("\",\"port\":\"");
        hostInfoStr.append(port);
        hostInfoStr.append("\"}");
        return hostInfoStr.toString();
    }

    /**
     * 判断数据是否为空值
     *
     * @param str
     * @return
     */
    public static boolean judgeIsEmpty(String str) {
        return str.length() > 0 && !str.equals(EMPTYSTR) && !str.equals(NULLSTR);
    }

    /**
     * String转properties
     *
     * @param settingString
     * @return
     */
    public static Properties convertStringToProperties(String settingString) {
        Properties properties = new Properties();
        if (judgeIsEmpty(settingString)) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(settingString.getBytes());
            try {
                properties.load(byteArrayInputStream);
            } catch (IOException e) {
                LOGGER.error("转换数据失败，失败原因：{}" , e.toString());
            }
        }
        return properties;
    }

    public static boolean judgeIsUrl(String url) {
        String regex = "^(https?|ftp|file|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern patt = Pattern.compile(regex);
        Matcher matcher = patt.matcher(url);
        boolean judgeMatch = matcher.matches();
        return judgeMatch;
    }

    /**
     * 获取Url中的Ip地址与端口号
     *
     * @param url
     * @return
     */
    public static List<String> getUrlsIpAndPort(String url) {
        List<String> urlInfo = new ArrayList<>();
        if (judgeIsUrl(url)) {
            String host = url.split("/")[2];
            if (host.contains(STR)) {
                String[] hostInfo = host.split(STR);
                urlInfo.add(hostInfo[0]);
                urlInfo.add(hostInfo[1]);
            } else {
                urlInfo.add(host);
                urlInfo.add("80");
            }
        }
        return urlInfo;
    }

    /**
     * 获取Mac地址
     *
     * @return
     * @throws Exception
     */
    public static String getMacAddress() throws Exception {
        InetAddress ia = InetAddress.getLocalHost();
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

    /**
     * 获取macList地址
     *
     * @return
     * @throws Exception
     */
    public static List<String> getMacList() throws Exception {
        List<String> macList = new ArrayList<>();
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            StringBuffer stringBuffer = new StringBuffer();
            NetworkInterface networkInterface = enumeration.nextElement();
            if (networkInterface != null) {
                byte[] bytes = networkInterface.getHardwareAddress();
                if (bytes != null) {
                    for (int i = 0; i < bytes.length; i++) {
                        if (i != 0) {
                            stringBuffer.append("-");
                        }
                        int tmp = bytes[i] & 0xff;
                        String str = Integer.toHexString(tmp);
                        if (str.length() == 1) {
                            stringBuffer.append("0" + str);
                        } else {
                            stringBuffer.append(str);
                        }
                    }
                    String mac = stringBuffer.toString().toUpperCase();
                    macList.add(mac);
                }
            }
        }
        return macList;
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2Md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("Md5");
        } catch (Exception e) {
            LOGGER.error("string2Md5 errMsg:{}", e);
            return STRING;
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toUpperCase();

    }

    /**
     * Blob转byte[]
     *
     * @param blob
     * @return
     */
    private static byte[] getBytes(Blob blob) {
        try {
            InputStream ins = blob.getBinaryStream();
            byte[] b = new byte[1024];
            int num = 0;
            String res = STRING;
            while ((num = ins.read(b)) != -1) {
                res += new String(b, 0, num);
            }
            return res.getBytes();
        } catch (Exception e) {
            LOGGER.error("getBytes errMsg:{}", e);
        }
        return null;
    }


    /**
     * 将blob转化为byte[],可以转化二进制流的
     *  
     *
     * @param blob
     * @return
     */
    public static byte[] blobToBytes(Blob blob) {
        InputStream is = null;
        byte[] b = null;
        try {
            is = blob.getBinaryStream();
            b = new byte[(int) blob.length()];
            if (is.read(b) > 0) {
                return b;
            }
        } catch (Exception e) {
            LOGGER.error("blobToBytes errMsg:{}", e);
        } finally {
            CloseableUtils.close(is);
        }
        return b;
    }

    /**
     * 判断是否为中文
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(ub)
                || Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(ub)
                || Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(ub)
                || Character.UnicodeBlock.GENERAL_PUNCTUATION.equals(ub)
                || Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(ub)
                || Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(ub);
    }

    public static boolean judgeHasChinese(String str) {
        if (str != null && str.length() > 0) {
            char[] chars = str.toCharArray();
            for (char ch : chars) {
                if (isChinese(ch)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static JSONObject getReturnJson(boolean success, Object data, String message) {
        JSONObject jsonObject = new JSONObject();
        if (success) {
            jsonObject.put(CODE, CODESUCCESS);
        } else {
            jsonObject.put(CODE, CODEERROR);
        }
        jsonObject.put(DATA, data);
        jsonObject.put(MESSAGE, message);
        return jsonObject;
    }

    public static boolean judgeListEmpty(List list) {
        return list != null && list.size() > 0;
    }

    public static Integer getScriptType(String osName) {
        if (osName.toLowerCase().contains(LINUX)) {
            return 1;
        } else if (osName.toLowerCase().contains(WINDOWS)) {
            return 2;
        } else {
            return 1;
        }
    }


    public static JSONObject creatIpStatus(Integer status, String contentLog) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("message", contentLog);
        return jsonObject;
    }

    public static Integer getMapValue(Map<Integer, Integer> map, int key) {
        if (map.containsKey(key)) {
            key = map.get(key);
        }
        return key;
    }

    public static String string2Utf8(String str) {
        return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    /**
     * Iso编码转utf-8
     *
     * @param str
     * @return
     */
    public static String getIsoToUtf8(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String newStr = "";
        try {
            newStr = new String(str.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("getIsoToUtf8 errMsg:{}", e);
        }
        return newStr;
    }

    /**
     * Iso编码转utf-8
     *
     * @param str
     * @return
     */
    public static String strToUtf8(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String newStr = "";
        try {
            newStr = new String(str.getBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("strToUtf8 errMsg:{}", e);
        }
        return newStr;
    }

    /**
     * 判断路径是否有不符合规范的路径
     *
     * @param path
     * @return
     */
    public static String checkPathIsRight(String path) {
        String windowPattern = "^[A-z]:\\\\(.+?\\\\?)+$";
        String linuxPattern = "^\\/(.+?\\/?)+$";
        String[] pathArr = path.split(",");
        for (String path1 : pathArr) {
            if (path1.matches(windowPattern) || path1.matches(linuxPattern)) {

            } else {
                return path1;
            }
        }
        return null;
    }

    /**
     * 去除list列表的首尾中括号以及不必要的空格
     *
     * @param list
     * @return
     */
    public static String changeListToString(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : list) {
            stringBuilder.append(String.valueOf(object));
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * 注！！！！！！→ 集合中的元素必须重写equals方法自行判断元素是否相同
     * 哈希地址相同 返回true
     * 如果两个参数都为空，则返回true
     * 如果有一项为空，则返回false
     * 如果数据长度不相同，则返回false
     * 集合1包含集合2中的所有元素，并且集合2也包含集合1中的所有元素 则返回true
     * 注！！！！！！→ 集合中的元素必须重写equals方法自行判断元素是否相同
     *
     * @param l0
     * @param l1
     * @return
     */
    public static boolean isListEqual(List l0, List l1) {
        if (l0 == l1) {
            return true;
        }
        if (l0 == null && l1 == null) {
            return true;
        }
        if (l0 == null || l1 == null) {
            return false;
        }
        if (l0.size() != l1.size()) {
            return false;
        }
        if (isEqualCollection(l0, l1) && l0.containsAll(l1) && l1.containsAll(l0)) {
            return true;
        }
        return false;
    }

    public static boolean isEqualCollection(Collection a, Collection b) {
        if (a.size() != b.size()) {
            // size是最简单的相等条件
            return false;
        }
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);

        // 转换map后，能去掉重复的，这时候size就是非重复项，也是先决条件
        if (mapa.size() != mapb.size()) {
            return false;
        }
        Iterator it = mapa.keySet().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            // 查询同一个obj，首先两边都要有，而且还要校验重复个数，就是map.value
            if (getFreq(obj, mapa) != getFreq(obj, mapb)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 以obj为key，可以防止重复，如果重复就value++
     * 这样实际上记录了元素以及出现的次数
     */
    public static Map getCardinalityMap(Collection coll) {
        Map count = new HashMap(coll.size());
        for (Iterator it = coll.iterator(); it.hasNext(); ) {
            Object obj = it.next();
            Integer c = (Integer) count.get(obj);
            if (c == null) {
                count.put(obj, 1);
            } else {
                count.put(obj, new Integer(c.intValue() + 1));
            }
        }
        return count;
    }

    private static final int getFreq(Object obj, Map freqMap) {
        Integer count = (Integer) freqMap.get(obj);
        if (count != null) {
            return count.intValue();
        }
        return 0;
    }


    public static boolean isNumeric(String str) {
        boolean strResult = str.matches("-?[0-9]+.？[0-9]*");
        return strResult;
    }

    /**
     * 获取本地ip地址
     *
     * @return 返回ipv4的所有地址，第一条数据固定为对外暴露的ipv4
     */
    public static List<String> getLocalIp() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip instanceof Inet4Address) {
                        list.add(ip.getHostAddress());
                        break;
                    }
                }
            }
            return list;
        } catch (Exception e) {
            LOGGER.error("IP地址获取失败" + e.toString());
            return list;
        }
    }
}
