package client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class RestHttpClientAppForProduct {
	public static void main(String[] args) throws Exception {

		// RestHttpClientAppForProduct.getProductTest_JsonSimple();
		RestHttpClientAppForProduct.addProductTest_Codehaus();

	}

	public static void getProductTest_JsonSimple() throws Exception {

		System.out.println("get프로덕트 JsonSimple 스타트");

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProduct/";

		url += "10001"; // getProduct 할 적당한 prodNo를 입력해 주세요~

		// HttpGet : Http Protocol 의 GET 방식 Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol 응답 Message 추상화
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response 확인
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response 중 entity(DATA) 확인
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server 에서 받은 Data 확인 ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> 내용읽기(JSON Value 확인)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);
	}

	public static void addProductTest_Codehaus() throws Exception {
		System.out.println("addProductTest 코드하우스 시작함");
		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/addProduct";

		// HttpGet : Http Protocol 의 POST 방식 Request
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		Product product = new Product();
		product.setProdName("2차제품");
		product.setProdDetail("코드하우스테스트중");
		product.setManuDate("11210302");
		product.setPrice(2222);
		product.setFileName("test2.jpg");

		ObjectMapper objectMapper = new ObjectMapper();
		// Object ==> JSON Value 로 변환
		String jsonValue = objectMapper.writeValueAsString(product);
		System.out.println("json화 된 product 객체 확인▽▼▽▼▽▼");
		System.out.println(jsonValue);

		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println("리퀘스트 이후 받아온 리스폰스 그대로 확인▽▼▽▼▽▼");
		System.out.println(httpResponse);

		// ==> Response 중 entity(DATA) 확인
		HttpEntity responseEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> 다른 방법으로 serverData 처리
		// System.out.println("[ Server 에서 받은 Data 확인 ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API 확인 : Stream 객체를 직접 전달
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("리스폰스 받아온 것을 다시 JSONObject로 파싱해서 확인▽▼▽▼▽▼");

		System.out.println(jsonobj);
		ObjectMapper responseMapper = new ObjectMapper();
		Product responseProduct = responseMapper.readValue(jsonobj.toString(), Product.class);

		System.out.println("리퀘스트 이후 받아온 리스폰스를 오브젝트매퍼로 프로덕트화 한걸 확인▽▼▽▼▽▼");

		System.out.println(responseProduct);

	}

}
