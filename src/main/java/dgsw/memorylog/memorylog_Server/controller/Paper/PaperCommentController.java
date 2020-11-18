package dgsw.memorylog.memorylog_Server.controller.Paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.PaperComment;
import dgsw.memorylog.memorylog_Server.domain.vo.http.Response;
import dgsw.memorylog.memorylog_Server.domain.vo.http.ResponseData;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentCreateVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentModifyVo;
import dgsw.memorylog.memorylog_Server.lib.AuthorizationCheck;
import dgsw.memorylog.memorylog_Server.service.PaperComment.PaperCommentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(value = "페이퍼 코멘트 API")
@RestController
@RequestMapping("/paperComment")
public class PaperCommentController {

    @Autowired
    private PaperCommentServiceImpl paperCommentService;

    @Autowired
    private AuthorizationCheck authorizationCheck;

    @PostMapping("/createPaperComment")
    @ApiOperation(value = "코멘트 생성")
    public Response createPaperComment(HttpServletRequest request, @RequestBody @Valid PaperCommentCreateVo paperCommentCreateVo) {
        try {
            authorizationCheck.check(request);
            Member member = (Member) request.getAttribute("member");
            paperCommentService.createPaperComment(member, paperCommentCreateVo);
            return new Response(HttpStatus.OK, "코멘트 생성 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @GetMapping("/show/{paperIdx}")
    @ApiOperation(value = "코멘트 조회")
    public Response getPaperComments(@PathVariable("paperIdx") Integer paperIdx) {
        try {
            List<PaperComment> paperComments = paperCommentService.getPaperComments(paperIdx);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("paperComments", paperComments);
            return new ResponseData(HttpStatus.OK, "롤링페이퍼 글 조회 성공", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @GetMapping("/showComment/{commentIdx}")
    public Response getPaperComment(@PathVariable("commentIdx") Integer commentIdx, HttpServletRequest request) {
        try {
            authorizationCheck.check(request);
            Member member = (Member) request.getAttribute("member");
            PaperComment paperComment = paperCommentService.getPaperComment(member, commentIdx);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("paperComment", paperComment);
            return new ResponseData(HttpStatus.OK, "롤링페이퍼 글 상세 조회 성공", data);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @PutMapping("/modify/{paperCommentIdx}")
    @ApiOperation(value = "코멘트 수정")
    public Response editPaperComment(@PathVariable("paperCommentIdx") @Valid Integer paperCommentIdx, HttpServletRequest request,
                                     @RequestBody @Valid PaperCommentModifyVo paperCommentModifyVo) {
        try {
            authorizationCheck.check(request);
            Member member = (Member) request.getAttribute("member");
            paperCommentService.editPaperComment(member, paperCommentIdx, paperCommentModifyVo);
            return new Response(HttpStatus.OK, "수정 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }

    @DeleteMapping("/delete/{paperCommentIdx}")
    @ApiOperation(value = "코멘트 삭제")
    public Response deletePaperComment(@PathVariable("paperCommentIdx") @Valid Integer paperCommentIdx, HttpServletRequest request) {
        try {
            Member member = (Member) request.getAttribute("member");
            paperCommentService.deletePaperComment(member, paperCommentIdx);
            return new Response(HttpStatus.OK, "삭제 성공.");
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류.");
        }
    }
}
