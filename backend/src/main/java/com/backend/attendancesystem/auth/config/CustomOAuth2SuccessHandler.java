package com.backend.attendancesystem.auth.config;

import com.backend.attendancesystem.auth.dto.GoogleUserInfo;
import com.backend.attendancesystem.common.exception.InvalidSessionAttribute;
import com.backend.attendancesystem.enums.RoleType;
import com.backend.attendancesystem.user.dto.request.UserRequest;
import com.backend.attendancesystem.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        UUID institutionId = null;

        if (session != null) {
            var sessionAttribute = (String) session.getAttribute(
                    CustomAuthorizationRequestRepository.INSTITUTION_ID_ATTR
            );

            try {
                institutionId = UUID.fromString(sessionAttribute);
            }
            catch (IllegalArgumentException e) {
                throw new InvalidSessionAttribute("Institution ID stored in session is invalid");
            }

        }

        OAuth2User principal =
                (OAuth2User) authentication.getPrincipal();

        //TODO: in the future maybe is a good idea change the name of the DTO in order to add more abstraction. Could be OAuth2UserInfo instead of GoogleUserInfo
        GoogleUserInfo userInfo = new GoogleUserInfo(
                principal.getAttribute("email"),
                principal.getAttribute("given_name"),
                principal.getAttribute("family_name")
        );

        System.out.println(
                "InstitutionId recuperado: " + institutionId
        );

        //if the user does not exist, create it with the institution id provided by the sessionAttribute
        if (userService.getUserByEmail(principal.getAttribute("email")).isEmpty()) {
            userService.saveUser(
                    new UserRequest(institutionId, RoleType.TEACHER, userInfo.name(), userInfo.lastName(), userInfo.email(), "")
            );
        }

        if (session != null) {
            session.removeAttribute(
                    CustomAuthorizationRequestRepository.INSTITUTION_ID_ATTR
            );
        }

        response.sendRedirect(
                frontendUrl + "/professor-panel"
        );
    }
}
