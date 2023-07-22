package com.memotalk.api.memouser.controller;

import com.memotalk.api.memouser.dto.*;
import com.memotalk.api.memouser.service.MemoUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/memo-user")
@RequiredArgsConstructor
@Tag(name = "Memo User", description = "메모 사용자 API")
@Slf4j
public class MemoUserController {

    private final MemoUserService memoUserService;

    @Operation(summary = "회원 가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일")
    })
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody MemoUserSignupRequestDTO dto) {
        memoUserService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemoUserSigninResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/signin")
    public ResponseEntity<MemoUserSigninResponseDTO> signin(@Valid @RequestBody MemoUserSigninRequestDTO reqeustDTO) {
        MemoUserSigninResponseDTO responseDTO = memoUserService.signin(reqeustDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Operation(summary = "내 정보 불러오기 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 불러오기 성공",
                    content = @Content(schema = @Schema(implementation = MemoUserSigninResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/info")
    public ResponseEntity<MemoUserResponseDTO> info(@Parameter(hidden = true) @AuthenticationPrincipal String email) {
        MemoUserResponseDTO responseDTO = memoUserService.info(email);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @Operation(summary = "계정 잠금 API")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 잠금 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @PatchMapping("/lock")
    public ResponseEntity<Void> lock(@Parameter(hidden = true) @AuthenticationPrincipal String email) {
        memoUserService.lock(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "계정 잠금 해제 API")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 잠금 해제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @PatchMapping("/unlock")
    public ResponseEntity<Void> unlock(@Parameter(hidden = true) @AuthenticationPrincipal String email, @RequestBody MemoUserUnlockRequestDTO requestDTO) {
        memoUserService.unlock(email, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "비밀번호 재설정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 잠금 해제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @PatchMapping("/password-reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody MemoUserPasswordResetRequestDTO requestDTO) {
        memoUserService.resetPassword(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
