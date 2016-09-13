package lz.lmf.pad.common;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class NetUtils {
    private String httpURL = "";
    private static String nameSpace = "http://tempuri.org/";
    private String method = "";
    private SoapObject request;
    private boolean flag;

    /**
     * Desc 构造方法
     * Author ShenYang
     * Date 2016/9/9
     * Time 9:24
     * Params
     */
    public NetUtils(boolean _flag, String URL, String Method) {
        flag = _flag; // 下载图片时flag为true，其余时候都是flase
        httpURL = URL; // webService 地址
        method = Method; // 方法名
        request = new SoapObject(nameSpace, method);
    }

    /**
     * Desc 访问webService的参数
     * Author ShenYang
     * Date 2016/9/9
     * Time 9:28
     * Params 
     */
    public void addProperty(String key, Object value) {
        request.addProperty(key, value);
    }

    /**
     * Desc 连接webService
     * Author ShenYang
     * Date 2016/9/9
     * Time 9:24
     * Params
     */
    public String start() throws IOException, XmlPullParserException {
        String result = null;
        // 提供版本信息
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        // 设置envelope为真，让他可以使用SoapSerializationEnvelope中的方法
        envelope.bodyOut = request;
        envelope.dotNet = true;
        // 分配一个对象给这个envelope做为这个通讯的外传信息
        envelope.setOutputSoapObject(request);
        // 发送请求给服务器，设置发送的内容
        HttpTransportSE ht = new HttpTransportSE(httpURL, 10000);
        // 发出请求
        ht.call(null, envelope);
        // 获取这个网络传输中返回的资源数据.
        if (flag == false) {

            result = envelope.getResponse().toString();
        } else {
            // Object o = envelope.getResponse();
            // System.out.println(o.toString());
            SoapObject so = (SoapObject) envelope.bodyIn;
            result = so.getProperty("uploadImgReturn").toString();

        }
        ht.getConnection().disconnect();
        return result;
    }
}
