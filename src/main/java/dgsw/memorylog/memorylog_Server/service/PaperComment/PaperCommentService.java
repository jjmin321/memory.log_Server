package dgsw.memorylog.memorylog_Server.service.PaperComment;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.PaperComment;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentCreateVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentModifyVo;

import java.util.List;

public interface PaperCommentService {
    public void createPaperComment(Member member, PaperCommentCreateVo paperCommentCreateVo);
    public List<PaperComment> getPaperComments(Integer paperIdx);
    public PaperComment getPaperComment(Member member, Integer commentIdx);
    public void editPaperComment(Member member, Integer paperCommentIdx, PaperCommentModifyVo paperCommentModifyVo);
    public void deletePaperComment(Member member, Integer paperCommentIdx);
}
