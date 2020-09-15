package com.wyjax.springresttemplate.controller;

import com.wyjax.springresttemplate.domain.MemberModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class MemberApiController {

    @GetMapping("/test")
    public ResponseEntity<?> getTest() {
        MemberModel memberModel = new MemberModel();
        memberModel.setName("엄정기");
        memberModel.setEmail("umjugnki@naver.com");

        return ResponseEntity.ok(memberModel);
    }
}
