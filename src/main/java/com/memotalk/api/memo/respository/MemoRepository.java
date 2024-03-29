package com.memotalk.api.memo.respository;

import com.memotalk.api.memo.entity.Memo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByWorkspace_Id(Long workspaceId);

    Slice<Memo> findAllByWorkspace_IdAndDescriptionContaining(Long workspaceId, String keyword, Pageable pageable);
}
