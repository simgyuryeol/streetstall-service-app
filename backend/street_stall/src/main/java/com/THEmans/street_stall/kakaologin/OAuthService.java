package com.THEmans.street_stall.kakaologin;

import com.THEmans.street_stall.Domain.KakaoAccessToken;
import com.THEmans.street_stall.Domain.KakaoProfile;
import com.THEmans.street_stall.Domain.User;
import com.THEmans.street_stall.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final UserRepository userRepository;

    private final String client_id = "26b62b4ed18c1bdfbcde187921450096";
    private final String client_secret = "xxx";
    private final String redirect_uri = "http://localhost:8880/login/oauth/kakao";
    private final String accessTokenUri = "https://kauth.kakao.com/oauth/token";
    private final String UserInfoUri = "https://kapi.kakao.com/v2/user/me";

    public KakaoAccessToken getAccessTokenByCode(String code) {  //인가코드로 인가토큰 요청 WebClient

        //요청 param (body)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id); // TODO REST_API_KEY 입력 //추후에 value로 처리
        params.add("redirect_uri", redirect_uri); // TODO 인가코드 받은 redirect_uri 입력
        params.add("code", code);
        //params.add("client_secret", client_secret); //클라이언트 시크릿 보안 강화 해당 기능 사용하면 추가해야됨

        //request
        WebClient webClient = WebClient.create(accessTokenUri);
        String response = webClient.post()
                .uri(accessTokenUri)
                .body(BodyInserters.fromFormData(params))
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" ) //요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //json 형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccessToken kakaoToken = null;

        try {
            kakaoToken = objectMapper.readValue(response, KakaoAccessToken.class);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return kakaoToken;
    }

    public KakaoProfile getUserInfoByAccessToken(String accessToken){ //토큰으로 유저 정보 요청
        //Http 요청
        WebClient webClient = WebClient.create(UserInfoUri);
        String response=webClient.post()
                .uri(UserInfoUri)
                .header("Authorization", "Bearer " + accessToken) //전송할 header작성, access_token 전송, Bearer 뒤에 빈칸
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);

        }catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    // 카카오 로그인 사용자 강제 호이ㅝㄴ가입
    @Transactional
    public User saveUser(String access_token){

        KakaoProfile profile = getUserInfoByAccessToken(access_token); //토큰으로 사용자 정보 받아오기

        User user = userRepository.findByUserid(profile.getId());

        //처음이용자 강제 회원가입
        if(user==null){
            user = User.builder()
                    .userid(profile.getId())
                    .name(profile.getKakao_account().getProfile().getNickname())
                    .email(profile.getKakao_account().getEmail())
                    .roles("User") //추후 변경
                    .build();

            userRepository.save(user);
        }
        return user;
    }

}
    /*

    public String getKakaoAccessToken (String code){ //인가코드로 인가토큰 요청
        String access_Token = "";
        String refresh_Token="";
        String reqURL="https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Post 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //Post 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=e4a81d5a6acbda948310e08e2eafc123"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            //요청을 통해 얻은 Json타입의 response 메시지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line=br.readLine())!=null){
                result+=line;
            }

            //Gson 라이브러리에 포함된 클래스로 json파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }


    public void createKakaoUser(String token) { //토큰을 이용해서 사용자 정보를 받아오기
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization","Bearer "+token);
            //전송할 header작성, access_token 전송, Bearer 뒤에 빈칸


            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            //요청을 통해 얻은 Json타입의 Response 메시지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line="";
            String result="";

            while ((line = br.readLine())!=null){
                result += line;
            }


            //Gson 라이브러리로 Json 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email="";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


} */
