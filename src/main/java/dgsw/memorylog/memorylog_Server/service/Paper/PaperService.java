package dgsw.memorylog.memorylog_Server.service.Paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.Paper;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperCreatePaperVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperHitPaperVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperModifyPaperVo;

import java.util.List;

public interface PaperService {
    public void createPaper(Member member, PaperCreatePaperVo paperCreatePaperVo);
    public PaperHitPaperVo showOnePaper(Integer paper_idx);
    public PaperHitPaperVo showOnlyCodePaper(Integer paper_idx, String code);
    public List<PaperHitPaperVo> showPublicPaper();
    public List<PaperHitPaperVo> getMyPaper(String name);
    public List<PaperHitPaperVo> searchPaperByTitle(String title);
    public List<PaperHitPaperVo> searchPaperByMemberName(String name);
    public void modifyPaper(Member member, Integer paperIdx, PaperModifyPaperVo paperModifyPaperVo);
    public List<PaperHitPaperVo> addLikeCount(List<Paper> papers);
}
