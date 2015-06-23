package onlineRetailers;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyc.util.Request;
import com.wyc.util.Response;

public class RequestResponseTest {
	public static void main(String[]args)throws Exception{
		new RequestResponseTest().test();
	}
	final static Logger logger = LoggerFactory.getLogger(RequestResponseTest.class);
	@Test
	public void test()throws Exception{
		URL url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb716e56c4805a7b7&secret=8760fe31ade287cb8b4ff15b09016426");
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		Request request = new Request(urlConnection);
		Response response = request.get(null);
		logger.debug("read:"+response.read());
	}
}
