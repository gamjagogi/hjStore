package com.macro.hjstore.service;


import com.macro.hjstore.core.annotation.MyLog;
import com.macro.hjstore.core.auth.jwt.MyJwtProvider;
import com.macro.hjstore.core.auth.session.MyUserDetails;
import com.macro.hjstore.core.auth.session.MyUserDetailsService;
import com.macro.hjstore.core.exception.Exception400;
import com.macro.hjstore.core.exception.Exception401;
import com.macro.hjstore.core.exception.Exception404;
import com.macro.hjstore.dto.user.UserRequest;
import com.macro.hjstore.dto.user.UserResponse;
import com.macro.hjstore.model.token.RefreshTokenEntity;
import com.macro.hjstore.model.token.TokenRepository;
import com.macro.hjstore.model.user.User;
import com.macro.hjstore.model.user.UserRepository;
import com.macro.hjstore.model.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// DB에서 사용자 정보 조회

// 비밀번호 확인

// 아이디, 비밀번호 확인이되면, UsernamePasswordToken객체를 사용해서, Authentication에 유저정보를 등록한다.\

//로그인 성공하면 액세스 토큰, 리프레시 토큰 발급. 리프레시 토큰의 uuid은 DB에 저장


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MyUserDetailsService userDetailsService;
    //private final S3Service s3Service;



    @MyLog
    public Pair<String, String> 로그인(UserRequest.LoginInDTO loginInDTO) {
        try {

            System.out.println(loginInDTO.getEmail() + " "+ loginInDTO.getPassword());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginInDTO.getEmail(), loginInDTO.getPassword());


            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();


            String accessjwt = MyJwtProvider.create(myUserDetails.getUser());
            Pair<String, RefreshTokenEntity> rtInfo = MyJwtProvider.createRefresh(myUserDetails.getUser());

            //로그인 성공하면 액세스 토큰, 리프레시 토큰 발급. 리프레시 토큰의 uuid은 DB에 저장
            tokenRepository.save(rtInfo.getSecond());

            return Pair.of(accessjwt, rtInfo.getFirst());
        } catch (Exception e) {
            throw new Exception401("인증되지 않았습니다.");
        }
    }


    @MyLog
    public UserResponse.LoginOutDTO 이메일로회원조회(String email) {
        User userPS = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception400(email,"해당 유저를 찾을 수 없습니다."));

        UserResponse.LoginOutDTO loginOutDTO = new UserResponse.LoginOutDTO(userPS.getUsername());
        return loginOutDTO;
    }

    @MyLog
    public void 회원가입(UserRequest.JoinInDTO joinInDTO){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User userPS = User.builder()
                .email(joinInDTO.getEmail())
                .password(encoder.encode(joinInDTO.getPassword()))
                .username(joinInDTO.getUsername())
                .birth(joinInDTO.getBirth())
                .role(UserRole.ROLE_USER)
                .status(true)
                .build();
        userRepository.save(userPS);
    }

}
