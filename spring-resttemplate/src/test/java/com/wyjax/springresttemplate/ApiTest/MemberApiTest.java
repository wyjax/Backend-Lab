package com.wyjax.springresttemplate.ApiTest;

import com.wyjax.springresttemplate.domain.MemberModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void 테스트() {
        ResponseEntity<MemberModel> responseEntity = testRestTemplate.getForEntity("/api/test", MemberModel.class);
        MemberModel memberModel = responseEntity.getBody();

        Assertions.assertThat("엄정기").isEqualTo(memberModel.getName());
    }
}
