package dgsw.memorylog.memorylog_Server.service.Paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.Paper;
import dgsw.memorylog.memorylog_Server.domain.entity.PaperLike;
import dgsw.memorylog.memorylog_Server.domain.repository.PaperLikeRepository;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperHitPaperVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperUpdateLikeVo;
import dgsw.memorylog.memorylog_Server.enums.UserLike;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PaperLikeServiceImpl implements PaperLikeService{
    @Autowired
    private PaperLikeRepository paperLikeRepo;

    /**
     * 롤링페이퍼 글에 따른 좋아요 수 반환
     * @return 좋아요 수
     */
    @Override
    public Integer getLikeCount(Integer paper_idx) {
        try {
            return paperLikeRepo.countByPaperIdx(paper_idx);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 롤링페이퍼 좋아요 여부에 따른 좋아요 / 좋아요 취소 메서드 실행
     * @return 없음
     */
    @Override
    public Enum<UserLike> updateLike(PaperUpdateLikeVo paperUpdateLikeVo) {
        try {
            Integer isLike = paperLikeRepo.countByPaperIdxAndMemberIdx(
                    paperUpdateLikeVo.getPaper().getIdx(),
                    paperUpdateLikeVo.getMember().getIdx());
            if (isLike == 1) {
                deleteLike(paperUpdateLikeVo);
                return UserLike.NO;
            } else {
                addLike(paperUpdateLikeVo);
                return UserLike.YES;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 좋아요
     * @return 없음
     */
    @Override
    public void addLike(PaperUpdateLikeVo paperUpdateLikeVo) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            PaperLike mappedPaperLike = modelMapper.map(paperUpdateLikeVo, PaperLike.class);
            paperLikeRepo.save(mappedPaperLike);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 좋아요 취소
     * @return 없음
     */
    @Override
    public void deleteLike(PaperUpdateLikeVo paperUpdateLikeVo) {
        try {
            paperLikeRepo.deleteByPaperIdxAndMemberIdx(
                        paperUpdateLikeVo.getPaper().getIdx(),
                        paperUpdateLikeVo.getMember().getIdx());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 좋아요 여부
     * @param paperHitPaperVo
     * @param request
     * @return true / false
     */
    @Override
    public boolean getIsLike(PaperHitPaperVo paperHitPaperVo, HttpServletRequest request) {
        try {
            if (request.getAttribute("member") == null) {
                return false;
            }

            Member member = (Member) request.getAttribute("member");

            Integer isLike = paperLikeRepo.countByPaperIdxAndMemberIdx(
                    paperHitPaperVo.getIdx(),
                    member.getIdx());

            return isLike == 1;
        } catch (Exception e) {
            throw e;
        }
    }
}
