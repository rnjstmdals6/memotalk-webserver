package com.memotalk.api.memo.service;

import com.memotalk.api.memo.dto.FileUploadRequestDTO;
import com.memotalk.api.memo.dto.MemoMarkImportantRequestDTO;
import com.memotalk.api.memo.dto.MemoRequestDTO;
import com.memotalk.api.memo.dto.MemoResponseDTO;
import com.memotalk.api.memo.entity.Memo;
import com.memotalk.api.memo.respository.MemoRepository;
import com.memotalk.api.workspace.entity.WorkSpace;
import com.memotalk.api.workspace.respository.WorkSpaceRepository;
import com.memotalk.exception.NotFoundException;
import com.memotalk.exception.enumeration.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final FileService fileService;

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
        memoRepository.save(new Memo(workSpace, memoRequestDto.getContent(), null));
    }

    public void uploadFile(FileUploadRequestDTO fileUploadRequestDTO) {
        WorkSpace workSpace = workSpaceRepository.findById(fileUploadRequestDTO.getWorkspaceId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.WORKSPACE_NOT_FOUND)
        );

        String s3FileUrl = fileService.upload(fileUploadRequestDTO.getMultipartFile());
        memoRepository.save(new Memo(workSpace, null, s3FileUrl));
    }

    public void deleteMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMO));
        if (memo.getS3FileUrl() != null) {
            fileService.fileDelete(memo.getS3FileUrl());
        }
        memoRepository.delete(memo);
    }

    public void markMemoImportant(MemoMarkImportantRequestDTO memoMarkImportantRequestDTO) {
        memoRepository.findById(memoMarkImportantRequestDTO.getMemoId()).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_MEMO)
        ).markImportant();
    }

    @Transactional(readOnly = true)
    public Slice<MemoResponseDTO> searchMemoWithKeyword(Long workspaceId, String keyword, Pageable pageable) {
        return memoRepository.findAllByWorkspace_IdAndDescriptionContaining(workspaceId, keyword, pageable)
                .map(MemoResponseDTO::new);
    }
}
