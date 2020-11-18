package dgsw.memorylog.memorylog_Server.controller.Paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.Paper;
import dgsw.memorylog.memorylog_Server.domain.vo.http.Response;
import dgsw.memorylog.memorylog_Server.domain.vo.http.ResponseData;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperCreatePaperVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperHitPaperVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperModifyPaperVo;
import dgsw.memorylog.memorylog_Server.enums.PaperScope;
import dgsw.memorylog.memorylog_Server.lib.AuthorizationCheck;
import dgsw.memorylog.memorylog_Server.lib.MakeRandomCode;
import dgsw.memorylog.memorylog_Server.service.Paper.PaperLikeServiceImpl;
import dgsw.memorylog.memorylog_Server.service.Paper.PaperService;
import dgsw.memorylog.memorylog_Server.service.Paper.PaperServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(value = "롤링페이퍼 API")
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperServiceImpl paperService;

    @Autowired
    private PaperLikeServiceImpl paperLikeService;

    @Autowired
    private AuthorizationCheck authorizationCheck;

    @Autowired
    private MakeRandomCode makeRandomCode;

    @PostMapping("/createPaper")
    @ApiOperation(value = "롤링페이퍼 생성")
    public Response createPaper(@RequestBody @Valid PaperCreatePaperVo paperCreatePaperVo, HttpServletRequest request) {
        try {
            authorizationCheck.check(request);
            Member member = (Member)request.getAttribute("member");
            paperCreatePaperVo.setCode(makeRandomCode.createCode(6));
            paperService.createPaper(member, paperCreatePaperVo);
            Map<String, Object> data = new HashMap<String, Object>();
            return new Response(HttpStatus.OK, "롤링페이퍼 생성 성공.");
        } catch (HttpClientErrorException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @PutMapping("/modifyPaper/{paperIdx}")
    @ApiOperation(value = "롤링페이퍼 수정")
    public Response modifyPaper(@PathVariable("paperIdx") Integer paperIdx, @RequestBody @Valid PaperModifyPaperVo paperModifyPaperVo,
                                HttpServletRequest request) {
        try {
            authorizationCheck.check(request);
            Member member = (Member)request.getAttribute("member");

            return new Response(HttpStatus.OK, "롤링페이퍼 수정 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @GetMapping("/getMyPaper")
    @ApiOperation(value = "내 롤링페이퍼 글 조회")
    public Response getMyPaper(HttpServletRequest request) {
        try {
            authorizationCheck.check(request);
            Member member = (Member)request.getAttribute("member");
            List<PaperHitPaperVo> papers = paperService.getMyPaper(member.getName());
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("Papers", papers);
            return new ResponseData(HttpStatus.OK, "롤링페이퍼 조회 성공", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @GetMapping("/showPaper")
    @ApiOperation(value = "롤링페이퍼 조회")
    public Response showPaper(@RequestParam(required = false) Integer paper_idx, String code, Boolean hit, HttpServletRequest request) {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            if (paper_idx == null) {
                List<PaperHitPaperVo> papers = paperService.showPublicPaper();
                if (hit != null && hit) {
                    papers.sort((o1, o2) -> {
                        if (o1.getLikeCount().equals(o2.getLikeCount()))
                            return 0;
                        return o1.getLikeCount() > o2.getLikeCount() ? -1 : 1;
                    });
                }
                data.put("Papers", papers);
            } else {
                PaperHitPaperVo papers = paperService.showOnePaper(paper_idx);
                switch (papers.getScope()) {
                    case PUBLIC:
                        break;
                    case ONLY_CODE:
                        papers = paperService.showOnlyCodePaper(paper_idx, code);
                        if (papers == null) {
                            throw new NullPointerException();
                        }
                        break;
                    case PRIVATE:
                        if (authorizationCheck.check(request)) {
                            break;
                        } else {
                            return new Response(HttpStatus.BAD_REQUEST, "이 롤링페이퍼에는 작성자만 접근 하실 수 있습니다.");
                        }
                }
                papers.setLike(paperLikeService.getIsLike(papers, request));
                data.put("Papers", papers);
            }

            return new ResponseData(HttpStatus.OK, "롤링페이퍼 조회 성공.", data);
        } catch (NullPointerException e) {
            return new Response(HttpStatus.BAD_REQUEST, "이 롤링페이퍼에 접근하시기 위해서는 접근 코드를 입력하시거나 작성자여야 합니다");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @GetMapping("/searchPaper")
    @ApiOperation(value = "롤링페이퍼 검색")
    public Response searchPaper(@RequestParam @Valid String target) {
        try {
            List<PaperHitPaperVo> searchedByTitle = paperService.searchPaperByTitle(target);
            List<PaperHitPaperVo> searchedByName = paperService.searchPaperByMemberName(target);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("SearchedByTitle", searchedByTitle);
            data.put("SearchedByName", searchedByName);
            return new ResponseData(HttpStatus.OK, "롤링페이퍼 검색 성공.", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
