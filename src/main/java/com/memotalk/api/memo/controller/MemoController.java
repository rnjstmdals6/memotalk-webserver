package com.memotalk.api.memo.controller;

import com.memotalk.api.memo.dto.MemoDeleteRequestDTO;
import com.memotalk.api.memo.dto.MemoMarkImportantRequestDTO;
import com.memotalk.api.memo.dto.MemoRequestDTO;
import com.memotalk.api.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemoController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final MemoService memoService;
    private static final String DESTINATION = "/sub/chat/room/";

    @MessageMapping(value = "/memo")
    public void message(@Valid MemoRequestDTO memoRequestDto) {
        memoService.createMemo(memoRequestDto);
        messagingTemplate.convertAndSend(DESTINATION + memoRequestDto.getWorkspaceId(), memoRequestDto);
    }

    @MessageMapping(value = "/memo/delete")
    public void deleteMessage(@Valid MemoDeleteRequestDTO memoDeleteRequestDto) {
        memoService.deleteMemo(memoDeleteRequestDto.getMemoId());
        messagingTemplate.convertAndSend(DESTINATION + memoDeleteRequestDto.getWorkspaceId(), memoDeleteRequestDto.getMemoId());
    }

    @MessageMapping(value = "/memo/mark-important")
    public void markMemoImportant(@Valid MemoMarkImportantRequestDTO memoMarkImportantRequestDTO) {
        memoService.markMemoImportant(memoMarkImportantRequestDTO);
        messagingTemplate.convertAndSend(DESTINATION + memoMarkImportantRequestDTO.getWorkspaceId(), memoMarkImportantRequestDTO);
    }
}
