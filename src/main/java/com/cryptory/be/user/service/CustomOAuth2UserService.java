package com.cryptory.be.user.service;

import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.OAuth2UserDto;
import com.cryptory.be.user.dto.PrincipalUserDetails;
import com.cryptory.be.user.exception.UserErrorCode;
import com.cryptory.be.user.exception.UserException;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
//        log.info("OAuth2User loadUser attributes: {}", attributes);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        log.info("OAuth2User loadUser registrationId: {}", registrationId);

        OAuth2UserDto oAuth2UserDto = OAuth2UserDto.of(attributes, registrationId);

        Optional<User> findUser = userRepository.findByProviderIdAndProviderName(oAuth2UserDto.getProviderId(), oAuth2UserDto.getProviderName());

        if (findUser.isEmpty()) {
            User user = userRepository.save(oAuth2UserDto.toUser());
            return new PrincipalUserDetails(user, attributes);
        }

        if(findUser.get().isDenied()) {
            throw new UserException(UserErrorCode.DENIED_USER);
        }

        return new PrincipalUserDetails(findUser.get(), attributes);
    }

}
