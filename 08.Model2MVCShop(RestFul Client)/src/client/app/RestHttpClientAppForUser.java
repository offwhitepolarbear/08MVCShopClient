package client.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.domain.Search;
import com.model2.mvc.service.domain.User;

public class RestHttpClientAppForUser {
	
	public static void main (String[] args) throws Exception {
		RestHttpClientAppForUser.updateUserTestByCodeHaus();
		//RestHttpClientAppForUser.userListTestByCodeHaus();
	}
	
	public static void updateUserTestByCodeHaus() throws Exception{
		
		System.out.println("updateUserTest �ڵ��Ͽ콺 ������");
		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/user/json/updateUser";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		
		
		//user�� ������ ������ �̸� �ּ� ���� �����ּҿ� ������ ��ġ�� ã���� userId�� �����ؼ� ������ ��
		
		User user = new User();
		user.setAddr("�ڵ��Ͽ콺����");
		user.setEmail("code@haus.com");
		user.setPhone("010-112-119");
		user.setUserName("�ڵ��Ͽ콺");
		user.setUserId("user19");
		
		//���� ���� �����ϰ� ������ JSONȭ �ؼ� �������� body�� �㵵�� �սô�
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = objectMapper.writeValueAsString(user);
		
		System.out.println("jsonȭ �� Search ��ü Ȯ�Ρ������");
		System.out.println(jsonValue);
		
		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println("������Ʈ ���� �޾ƿ� �������� �״�� Ȯ�Ρ������");
		System.out.println(httpResponse);
		
		HttpEntity responseEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("�������� �޾ƿ� ���� �ٽ� JSONObject�� �Ľ��ؼ� Ȯ�Ρ������");

		System.out.println(jsonobj);
		ObjectMapper responseMapper = new ObjectMapper();
		User updatedUser= responseMapper.readValue(jsonobj.toString(), User.class);

		System.out.println("������Ʈ ���� �޾ƿ� ���������� ������Ʈ���۷� mapȭ �Ѱ� Ȯ�Ρ������");

		System.out.println(updatedUser);
	}
	
	public static void userListTestByCodeHaus() throws Exception {
		System.out.println("userListTest �ڵ��Ͽ콺 ������");
		// HttpClient : Http Protocol �� client �߻�ȭ
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/user/json/listUser";

		// HttpGet : Http Protocol �� POST ��� Request
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		
		//������� �� ��ġ�� ���⼭ �����ؼ� �ٵ� �־��ݽô�
		Search search = new Search();
		search.setCurrentPage(2);
		search.setPageSize(3);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = objectMapper.writeValueAsString(search);
		
		System.out.println("jsonȭ �� Search ��ü Ȯ�Ρ������");
		System.out.println(jsonValue);
		
		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response Ȯ��
		System.out.println("������Ʈ ���� �޾ƿ� �������� �״�� Ȯ�Ρ������");
		System.out.println(httpResponse);
		
		HttpEntity responseEntity = httpResponse.getEntity();

		// ==> InputStream ����
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("�������� �޾ƿ� ���� �ٽ� JSONObject�� �Ľ��ؼ� Ȯ�Ρ������");

		System.out.println(jsonobj);
		ObjectMapper responseMapper = new ObjectMapper();
		Map<String, Object> map = responseMapper.readValue(jsonobj.toString(), Map.class);

		System.out.println("������Ʈ ���� �޾ƿ� ���������� ������Ʈ���۷� mapȭ �Ѱ� Ȯ�Ρ������");

		System.out.println(map);
	}

}
