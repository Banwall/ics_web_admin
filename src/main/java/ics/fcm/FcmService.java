package ics.fcm;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import ics.fcm.dto.FcmResultDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FcmService {

	private String firebaseSdkPath = "static/resources/google/icn-subway-admin-firebase-adminsdk-v0y0b-71621ffd9e.json";
	private String PROJECT_ID = "icn-subway-admin";
	private String BASE_URL = "https://fcm.googleapis.com";
	private final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";
	private final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	private final String[] SCOPES = { MESSAGING_SCOPE };
	private final String MESSAGE_KEY = "message";
	
	public FcmResultDTO sendMessage(String token,String title, String body) throws Exception{
		JsonObject message = makeMessage( token, title,  body);
		FcmResultDTO fcmResultDTO = sendMessage(message);
		return fcmResultDTO;
	}
	
	/**
	 * json format
	 * @param token
	 * @param title
	 * @param body
	 * @return
	 */
	private JsonObject makeMessage(String token,String title, String body) {
		return makeMessage(token, title,  body, null);
	}
	
	/**
	 * json format
	 * @param token
	 * @param title
	 * @param body
	 * @param dataMap
	 * @return
	 */
	private JsonObject makeMessage(String token, String title, String body, HashMap dataMap) {
		
		JsonObject data = null;
		if(dataMap != null) {
			data =  new JsonObject();
			Iterator<String> iter = dataMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				String value = String.valueOf(dataMap.get(key));
				data.addProperty(key, value);
			}
			data.addProperty("title", title);
			data.addProperty("body", body);
			
		}
		
		JsonObject jMessage = new JsonObject();
		if(data!=null) {
			jMessage.add("data", data);
		}
		
		//JsonObject android = new JsonObject();
		//android.addProperty("ttl", "4500s");
		//jMessage.add("android", android);

		
		JsonObject jNotification = new JsonObject();
		jNotification.addProperty("title", title);
		jNotification.addProperty("body", body);
		jNotification.addProperty("image", "null");
		jMessage.add("notification", jNotification);
		
		jMessage.addProperty("token", token);
		
		//	추가
		JsonObject apns = new JsonObject();
		JsonObject headers = new JsonObject();
		JsonObject payload = new JsonObject();
		JsonObject aps = new JsonObject();
		//headers.addProperty("apns-expiration", "1604750400");
		//apns.add("headers", headers);
		aps.addProperty("sound", "default");
		payload.add("aps", aps);
		apns.add("payload", payload);
		jMessage.add("apns", apns);
		
		JsonObject jFcm = new JsonObject();
		jFcm.add(MESSAGE_KEY, jMessage);
		return jFcm;
	}

	
	/**
	 * 단일건 전송
	 * @param fcmMessage
	 * @return
	 * 실패갯수, 성공갯수, 푸쉬번호 
	 * @throws Exception
	 */
	private FcmResultDTO sendMessage(JsonObject fcmMessage) throws Exception {
		Integer success = 0;
		Integer fail = 0;
		HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
		HttpURLConnection connection = getConnection();
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(fcmMessage.toString());
		writer.flush();
		writer.close();

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			String response = inputstreamToString(connection.getInputStream());
			log.debug("success");
			prettyPrint(fcmMessage);
			success++;
		} else {
			String response = inputstreamToString(connection.getErrorStream());
			log.debug("Unable to send message to Firebase:");
			log.debug(response);
			log.debug("send fail....");
			prettyPrint(fcmMessage);
			fail++;
		}
		
		FcmResultDTO fcmResultDTO = FcmResultDTO.builder()
				.success(success)
				.fail(fail)
				.build();
		
		return fcmResultDTO;
	}
	

    
	/**
	 * 프린트. 로그
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	private String inputstreamToString(InputStream inputStream) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}
	

	/**
	 * HTTP 헤더 작성
	 * @return
	 * @throws Exception
	 */
	
	private HttpURLConnection getConnection() throws Exception { // [START use_access_token]
		URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
		return httpURLConnection; // [END use_access_token]
	}
	
	/**
	 * log
	 * @param jsonObject
	 */
	private static void prettyPrint(JsonObject jsonObject) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		log.debug(gson.toJson(jsonObject) + "\n");
	}

	/**
	 * 토큰 발급
	 * @return
	 * @throws Exception
	 */
	@Bean
	private String getAccessToken() throws Exception {
		ClassPathResource resource = new ClassPathResource(firebaseSdkPath);
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(resource.getInputStream())
				.createScoped(Arrays.asList(SCOPES));
				//.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
		// GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
		// REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
		// googleCredentials.refreshAccessToken();
		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}
}
