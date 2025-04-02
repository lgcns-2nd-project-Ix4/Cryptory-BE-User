//package com.cryptory.be.admin.interceptor;
//
//import com.cryptory.be.admin.domain.TrafficLog;
//import com.cryptory.be.admin.repository.TrafficLogRepository;
//import com.cryptory.be.user.domain.User;
//import com.cryptory.be.user.dto.PrincipalUserDetails;
//import com.cryptory.be.user.repository.UserRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.time.LocalDateTime;
//
///**
// * packageName    : com.cryptory.be.admin.interceptor
// * fileName       : TrafficLogInterceptor
// * author         : 조영상
// * date           : 2/26/25
// * description    : 자동 주석 생성
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 2/26/25         조영상        최초 생성
// */
//@Component
//@RequiredArgsConstructor
//public class TrafficLogInterceptor implements HandlerInterceptor {
//    private static final Logger log = LoggerFactory.getLogger(TrafficLogInterceptor.class);
//    private final TrafficLogRepository trafficLogRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
//        String ipAddress = request.getRemoteAddr();
//        String userAgent = request.getHeader("User-agent");
//        String path = request.getRequestURI();
//        LocalDateTime timestamp = LocalDateTime.now();
//
//        User user = null;
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if(authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof PrincipalUserDetails) {
//                PrincipalUserDetails userDetails = (PrincipalUserDetails) principal;
//                user = userDetails.getUser();
//                log.info("OAuth로 로그인한 사용자의 정보로 User 객체를 받아옴 {}", user);
//            }
//        }
//        if (user == null) {
//            log.info("로그인하지 않은 사용자의 요청. IP: {}, Path: {}", ipAddress, path);
//        }
//        TrafficLog trafficLog = TrafficLog.builder()
//                .user(user)
//                .ipAddress(ipAddress)
//                .userAgent(userAgent)
//                .path(path)
//                .timestamp(timestamp)
//                .build();
//
//        try{
//            trafficLogRepository.save(trafficLog);
//        } catch (Exception e){
//            log.error("TrafficLog 저장 실패: {}", e.getMessage(), e);
//        }
//        return  true;
//    }
//}
