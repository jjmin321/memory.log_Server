package dgsw.memorylog.memorylog_Server.domain.vo.paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.enums.PaperScope;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PaperHitPaperVo {
    private Integer idx;

    private Member member;

    private String title;

    private PaperScope scope;

    private String code;

    private boolean isLike;

    private Integer likeCount;

    private String thumbnail;

    private LocalDateTime endTime;

    private Date createdAt;

    private Date updatedAt;
}
