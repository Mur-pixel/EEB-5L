package com.example.account.controller;

import com.example.account.controller.request.LoginAccountRequest;
import com.example.account.controller.request.RegisterAccountRequest;
import com.example.account.controller.response.IdAccountResponse;
import com.example.account.controller.response.LoginAccountResponse;
import com.example.account.controller.response.RegisterAccountResponse;
import com.example.account.entity.Account;
import com.example.account.redis_cache.RedisCacheService;
import com.example.account.repository.AccountRepository;
import com.example.account.utility.EncryptionUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    // 사용자 등록 및 Redis 캐시
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RedisCacheService redisCacheService;
    
    // 사용자 등록
    // RegisterAccountRequest 라는 사용자 등록 요청 형태를 수신
    @PostMapping("/register")
    public RegisterAccountResponse register(@RequestBody RegisterAccountRequest request) {
        // 수신한 요청 형태를 다루는 Domain Entity에 맞게 toDomainEntity() 형태로 만듭니다.
        // 그래서 결론적으로 요청 정보를 기반으로 한 요청된 Account 정보를 작성합니다.
        Account requestedAccount = request.toAccount();
        // accountRepository <- DB 제어 의존성(JPA)의 save() 기능을 활용하여
        // 요청 처리하기 바라는 Account 정보를 DB에 저장합니다.
        Account createdAccount = accountRepository.save(requestedAccount);

        // 그리고 우리가 알고 싶은 것은 회원 가입이 되었냐 안되었냐가 궁금합니다.
        // createdAccount가 null이 아니라면 true를 설정해서 리턴합니다.
        // 가만 보면 Response.from()은 실제 UI에서 기대하는 형태로 변환하여
        // 변환된 정보를 전달하는 임무를 수행한다 봐도 무방합니다.
        return RegisterAccountResponse.from(createdAccount);
    }

    // LoginAccountRequest 라고 해서 로그인 시 요청하는 정보를 수신
    @PostMapping("/login")
    public LoginAccountResponse login(@RequestBody LoginAccountRequest request) {
        // 사용자가 로그인을 위해 요청한 userId
        String requestedUserId = request.getUserId();

        // 그래서 사용자가 요청한 userId가 진짜 존재하는가?
        // 실제 default는 findById였고 변형 버전은 findByXXX 였음.
        // findByXXX에서 XXX는 실제 Entity 내부의 멤버 필드로 존재해야 하는 조건이 있었음.
        Optional<Account> optionalAccount = accountRepository.findByUserId(requestedUserId);

        // 만약 존재하지 않는다면
        if (optionalAccount.isEmpty()) {
            // userToken == null <- 승인 거절
            return new LoginAccountResponse(null);
        }

        // 존재한다면 찾은 사용자 정보를 가져옴
        Account account = optionalAccount.get();
        // 사용자가 로그인을 위해 전송한 password
        String requestedPassword = request.getPassword();

        // 실제 DB에 존재하는 password와 사용자가 로그인 요청한 비밀번호의 비교 매칭을 진행
        boolean matched = EncryptionUtility.matches(requestedPassword, account.getPassword());
        // 부정의 부정은 참이므로 매칭이 안된 경우 `!` 를 통해 부정함
        if (!matched) {
            return new LoginAccountResponse(null);  // 비밀번호 틀림
        }

        // UUID는 무슨 짓을 절대적으로 유일성을 가지는 id 값
        // 사실 MSA에서 특정 Entity의 id 값을 숫자로하는 것은 위험합니다.
        // DB 마다 첫 번째 숫자는 1번인데 클러스터 환경에서 모두 1을 가지게 되므로
        // findById(1) 상황에서 유일성이 깨지게 됩니다.
        // 그렇기 때문에 실제 MSA 상황에서 id를 아래와 같이 UUID 기반의 String으로 처리하게 됩니다.
        String token = UUID.randomUUID().toString();
        // 이후 비밀번호 매칭이 된 사용자(account)의 id 값을 위의 절대적 유일성을 보장하는 UUID와 맵핑합니다.
        // UUID가 key 값이 되고 사용자의 id(pk) 가 value가 됩니다.
        // 마지막에 Duration.ofDays(1) 은 이 redis 토큰을 하루동안 보장해줌을 의미합니다(컴퓨터가 꺼지지 않는 이상)
        // redis는 메모리 기반이기 때문에 컴퓨터가 꺼지면 휘발되기 때문입니다.
        redisCacheService.setKeyAndValue(token, account.getId(), Duration.ofDays(1));

        // 이후 UUID를 통해 생성한 userToken을 리턴합니다.
        return LoginAccountResponse.from(token);
    }

    @GetMapping("/find-id")
    public ResponseEntity<IdAccountResponse> getAccountId(@RequestHeader("Authorization") String token) {
        String pureToken = extractToken(token);
        String accountId = redisCacheService.getValueByKey(pureToken, String.class);

        if (accountId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Account account = accountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(() -> new RuntimeException("사용자 존재하지 않음"));

        return ResponseEntity.ok(new IdAccountResponse(account.getId()));
    }

    private String extractToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
