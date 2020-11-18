package dgsw.memorylog.memorylog_Server.service.Paper;

import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperHitPaperVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperUpdateLikeVo;
import dgsw.memorylog.memorylog_Server.enums.UserLike;

import javax.servlet.http.HttpServletRequest;

public interface PaperLikeService {
    public Integer getLikeCount(Integer paper_idx);
    public Enum<UserLike> updateLike(PaperUpdateLikeVo paperUpdateLikeVo);
    public void addLike(PaperUpdateLikeVo paperUpdateLikeVo);
    public void deleteLike(PaperUpdateLikeVo paperUpdateLikeVo);
    public boolean getIsLike(PaperHitPaperVo paperHitPaperVo, HttpServletRequest request);
}
