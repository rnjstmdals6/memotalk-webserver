package com.memotalk.api.memo.service;

import com.memotalk.api.memo.dto.MemoMarkImportantRequestDTO;
import com.memotalk.api.memo.dto.MemoResponseDTO;
import com.memotalk.api.memo.entity.Memo;
import com.memotalk.api.memo.respository.MemoRepository;
import com.memotalk.api.workspace.entity.WorkSpace;
import com.memotalk.api.workspace.respository.WorkSpaceRepository;
import com.memotalk.api.memo.dto.MemoRequestDTO;
import com.memotalk.exception.NotFoundException;
import com.memotalk.exception.enumeration.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoService {
    private final MemoRepository memoRepository;
    private final WorkSpaceRepository workSpaceRepository;
    @Transactional(readOnly = true)
    public List<MemoResponseDTO> getMemoList(Long workspaceId) {
        return memoRepository.findAllByWorkspace_Id(workspaceId)
                .stream().map(MemoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void createMemo(MemoRequestDTO memoRequestDto) {
        WorkSpace workSpace = workSpaceRepository.findById(memoRequestDto.getWorkspaceId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.WORKSPACE_NOT_FOUND)
        );

        memoRepository.save(new Memo(workSpace, memoRequestDto.getContent()));
    }

    public void deleteMemo(Long memoId) {
        memoRepository.deleteById(memoId);
    }

    public void markMemoImportant(MemoMarkImportantRequestDTO memoMarkImportantRequestDTO) {
        memoRepository.findById(memoMarkImportantRequestDTO.getMemoId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_MEMO)
        ).markImportant();
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDTO> searchMemoWithKeyword(Long workspaceId, String keyword) {
        return memoRepository.findAllByWorkspace_IdAndDescriptionContaining(workspaceId, keyword)
                .stream().map(MemoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
