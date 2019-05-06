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
		
		System.out.println("updateUserTest 코드하우스 시작함");
		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/user/json/updateUser";
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		
		
		//user에 수정될 정보인 이름 주소 전번 메일주소와 수정할 위치를 찾아줄 userId를 세팅해서 보내야 함
		
		User user = new User();
		user.setAddr("코드하우스에삼");
		user.setEmail("code@haus.com");
		user.setPhone("010-112-119");
		user.setUserName("코드하우스");
		user.setUserId("user19");
		
		//유저 세팅 종료하고 유저를 JSON화 해서 프로토콜 body에 담도록 합시다
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = objectMapper.writeValueAsString(user);
		
		System.out.println("json화 된 Search 객체 확인▽▼▽▼▽▼");
		System.out.println(jsonValue);
		
		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println("리퀘스트 이후 받아온 리스폰스 그대로 확인▽▼▽▼▽▼");
		System.out.println(httpResponse);
		
		HttpEntity responseEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("리스폰스 받아온 것을 다시 JSONObject로 파싱해서 확인▽▼▽▼▽▼");

		System.out.println(jsonobj);
		ObjectMapper responseMapper = new ObjectMapper();
		User updatedUser= responseMapper.readValue(jsonobj.toString(), User.class);

		System.out.println("리퀘스트 이후 받아온 리스폰스를 오브젝트매퍼로 map화 한걸 확인▽▼▽▼▽▼");

		System.out.println(updatedUser);
	}
	
	public static void userListTestByCodeHaus() throws Exception {
		System.out.println("userListTest 코드하우스 시작함");
		// HttpClient : Http Protocol 의 client 추상화
		HttpClient httpClient = new DefaultHttpClient();

		String url = "http://127.0.0.1:8080/user/json/listUser";

		// HttpGet : Http Protocol 의 POST 방식 Request
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		
		
		//보내줘야 할 서치를 여기서 세팅해서 바디에 넣어줍시다
		Search search = new Search();
		search.setCurrentPage(2);
		search.setPageSize(3);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = objectMapper.writeValueAsString(search);
		
		System.out.println("json화 된 Search 객체 확인▽▼▽▼▽▼");
		System.out.println(jsonValue);
		
		HttpEntity httpEntity = new StringEntity(jsonValue, "utf-8");

		httpPost.setEntity(httpEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);

		// ==> Response 확인
		System.out.println("리퀘스트 이후 받아온 리스폰스 그대로 확인▽▼▽▼▽▼");
		System.out.println(httpResponse);
		
		HttpEntity responseEntity = httpResponse.getEntity();

		// ==> InputStream 생성
		InputStream is = responseEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		
		JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
		System.out.println("리스폰스 받아온 것을 다시 JSONObject로 파싱해서 확인▽▼▽▼▽▼");

		System.out.println(jsonobj);
		ObjectMapper responseMapper = new ObjectMapper();
		Map<String, Object> map = responseMapper.readValue(jsonobj.toString(), Map.class);

		System.out.println("리퀘스트 이후 받아온 리스폰스를 오브젝트매퍼로 map화 한걸 확인▽▼▽▼▽▼");

		System.out.println(map);
	}

}
