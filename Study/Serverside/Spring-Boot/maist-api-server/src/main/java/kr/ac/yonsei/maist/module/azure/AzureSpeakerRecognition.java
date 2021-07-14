/**
 * A module that calls Azure's'Speaker Recognition API' that returns a best match list based on the similarity score for a given list of IDs.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.03.22
 */
package kr.ac.yonsei.maist.module.azure;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.yonsei.maist.domain.recognition.dto.EnrollmentResponseDto;
import kr.ac.yonsei.maist.domain.recognition.dto.ProfileIdentifyResponseDto;
import kr.ac.yonsei.maist.domain.recognition.dto.ProfileListResponseDto;
import kr.ac.yonsei.maist.domain.recognition.dto.ProfileResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class AzureSpeakerRecognition {

    @Value("${module.azure.key}")
    private String key;

    @Value("${module.azure.endpoint}")
    private String endpoint;

    @Value("${module.azure.recognition.profile-uri}")
    private String apiUri;

    private String httpBody = "{\'locale\': \'en-us\'}";

    /**
     * Add an enrollment to existing profile.
     * If the voice print was created before, it gets recreated from all existing enrollment audios including the new one.
     * @param profileId profile ID
     * @param files audio file
     * @return EnrollmentResponseDto
     */
    public EnrollmentResponseDto createEnrollment(String profileId, byte[] files) throws Exception {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, "audio/wav");
            httpHeaders.set("Ocp-Apim-Subscription-Key", key);

            String url = endpoint + apiUri + "/" + profileId + "/enrollments?ignoreMinLength=true";
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<?> httpEntity = new HttpEntity<>(files, httpHeaders);
            ResponseEntity<String> response= restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, httpEntity, String.class);

            log.info(url);
            log.info(response.getStatusCode().toString());
            log.info(response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            EnrollmentResponseDto enrollment = mapper.readValue(response.getBody(), EnrollmentResponseDto.class);

            return enrollment;

        } catch (HttpClientErrorException e) {
            switch (e.getStatusCode()){
                case BAD_REQUEST :
                case FORBIDDEN:
                case NOT_FOUND :
                    throw new IllegalArgumentException();
                case UNAUTHORIZED:
                default:
                    throw new Exception();
            }
        }

    }

    /**
     * Create a new speaker profile.
     * @return ProfileResponseDto
     */
    public ProfileResponseDto createProfile() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.set("Ocp-Apim-Subscription-Key", key);

        String url = endpoint + apiUri;
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpBody, httpHeaders);

        ResponseEntity<String> response= restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, httpEntity, String.class);
        log.info(url);
        log.info(response.getStatusCode().toString());
        log.info(response.getBody());

        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            ObjectMapper mapper = new ObjectMapper();
            ProfileResponseDto profile = mapper.readValue(response.getBody(), ProfileResponseDto.class);
            return profile;
        }
        else {
            log.info("status-code: "+response.getStatusCode());
            log.info("error-message: "+response.getBody());
            throw new Exception();
        }
    }

    /**
     * Deletes an existing profile.
     * @param profileId profile ID
     */
    public void deleteProfile(String profileId) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.set("Ocp-Apim-Subscription-Key", key);

        String url = endpoint + apiUri + "/" + profileId;
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.DELETE, httpEntity, String.class);

        log.info(url);
        log.info(response.getStatusCode().toString());
        log.info(response.getBody());

        if(response.getStatusCode().equals(HttpStatus.OK)){
            log.info("success");
        }
        else {
            log.info("status-code: "+response.getStatusCode());
            log.info("error-message: "+response.getBody());
            throw new Exception();
        }
    }

    /**
     * Identifies who is speaking in input audio among a list of candidate profiles.
     * @param profileIds list of profile IDs
     * @param files audio file
     * @return ProfileIdentifyResponseDto
     */
    public ProfileIdentifyResponseDto identifyProfile(String profileIds, byte[] files) throws Exception {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, "audio/wav");
            httpHeaders.set("Ocp-Apim-Subscription-Key", key);

            log.info(profileIds);
            String url = endpoint + apiUri + "/identifySingleSpeaker?profileIds="+ profileIds +"&ignoreMinLength=true";
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<?> httpEntity = new HttpEntity<>(files, httpHeaders);

            ResponseEntity<String> response= restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, httpEntity, String.class);

            log.info(url);
            log.info(response.getStatusCode().toString());
            log.info(response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            ProfileIdentifyResponseDto result = mapper.readValue(response.getBody(), ProfileIdentifyResponseDto.class);
            return result;

        } catch (HttpClientErrorException e){
            switch (e.getStatusCode()){
                case BAD_REQUEST :
                case NOT_FOUND :
                    throw new IllegalArgumentException();
                case UNAUTHORIZED:
                default:
                    throw new Exception();
            }
        }
    }

    /**
     * Get a list of profiles.
     * Profiles are sorted alphabetically by ProfileId.
     * @return ProfileListResponseDto
     */
    public ProfileListResponseDto findAllProfile() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.set("Ocp-Apim-Subscription-Key", key);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(endpoint + apiUri);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        log.info(endpoint + apiUri);
        log.info(response.getStatusCode().toString());
        log.info(response.getBody());

        if(response.getStatusCode().equals(HttpStatus.OK)){
            ObjectMapper mapper = new ObjectMapper();
            ProfileListResponseDto profileList = mapper.readValue(response.getBody(), ProfileListResponseDto.class);
            return profileList;
        }
        else {
            log.info("status-code: "+response.getStatusCode());
            log.info("error-message: "+response.getBody());
            throw new Exception();
        }
    }

    /**
     * Resets existing profile to its original creation state.
     * @param profileId profile ID
     */
    public void resetProfile(String profileId) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.set("Ocp-Apim-Subscription-Key", key);

        String url = endpoint + apiUri + "/" + profileId + "/reset";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response= restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, httpEntity, String.class);

        log.info(endpoint + apiUri);
        log.info(response.getStatusCode().toString());
        log.info(response.getBody());

        if(response.getStatusCode().equals(HttpStatus.NO_CONTENT)){
            log.info("success");
        }
        else {
            log.info("status-code: "+response.getStatusCode());
            log.info("error-message: "+response.getBody());
            throw new IllegalArgumentException();
        }
    }
}
