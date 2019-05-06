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

		System.out.println("get���δ�Ʈ JsonSimple ��ŸƮ");

		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/getProduct/";

		url += "10001"; // getProduct �� ������ prodNo�� �Է��� �ּ���~

		// HttpGet : Http Protocol �� GET ��� Request
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		// HttpResponse : Http Protocol ���� Message �߻�ȭ
		HttpResponse httpResponse = httpClient.execute(httpGet);

		// ==> Response Ȯ��
		System.out.println(httpResponse);
		System.out.println();

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity httpEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		String serverData = br.readLine();
		System.out.println(serverData);

		// ==> �����б�(JSON Value Ȯ��)
		JSONObject jsonobj = (JSONObject) JSONValue.parse(serverData);
		System.out.println(jsonobj);
	}

	public static void addProductTest_Codehaus() throws Exception {
		System.out.println("addProductTest �ڵ��Ͽ콺 ������");
		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/product/json/addProduct";

		// HttpGet : Http Protocol �� POST ��� Request
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		Product product = new Product();
		product.setProdName("2����ǰ");
		product.setProdDetail("�ڵ��Ͽ콺�׽�Ʈ��");
		product.setManuDate("11210302");
		product.setPrice(2222);
		product.setFileName("test2.jpg");

		ObjectMapper objectMapper = new ObjectMapper();
		// Object ==> JSON Value �� ��ȯ
		String jsonValue = objectMapper.writeValueAsString(product);
		System.out.println("jsonȭ �� product ��ü Ȯ�Ρ������");
		System.out.println(jsonValue);

		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println("������Ʈ ���� �޾ƿ� �������� �״�� Ȯ�Ρ������");
		System.out.println(httpResponse);

		// ==> Response �� entity(DATA) Ȯ��
		HttpEntity responseEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		// ==> �ٸ� ������� serverData ó��
		// System.out.println("[ Server ���� ���� Data Ȯ�� ] ");
		// String serverData = br.readLine();
		// System.out.println(serverData);

		// ==> API Ȯ�� : Stream ��ü�� ���� ����
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("�������� �޾ƿ� ���� �ٽ� JSONObject�� �Ľ��ؼ� Ȯ�Ρ������");

		System.out.println(jsonobj);
		ObjectMapper responseMapper = new ObjectMapper();
		Product responseProduct = responseMapper.readValue(jsonobj.toString(), Product.class);

		System.out.println("������Ʈ ���� �޾ƿ� ���������� ������Ʈ���۷� ���δ�Ʈȭ �Ѱ� Ȯ�Ρ������");

		System.out.println(responseProduct);

	}

}
