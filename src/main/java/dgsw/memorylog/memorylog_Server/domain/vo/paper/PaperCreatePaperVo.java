package dgsw.memorylog.memorylog_Server.domain.vo.paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.enums.PaperScope;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 롤링페이퍼 생성 오브젝트
 */
@Getter
@Setter
public class PaperCreatePaperVo {
    @NotBlank
    private String title;

    @NotNull
    private PaperScope scope;

    private String code;

    private String thumbnail;

    private LocalDateTime endTime;
}
