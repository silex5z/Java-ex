package kr.co.nutellaevent.board.controller.api;

import java.util.HashMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import kr.co.nutellaevent.board.dto.BoardDto;
import kr.co.nutellaevent.board.service.BoardServiceImpl;
import kr.co.nutellaevent.util.ResponseVO;
import kr.co.nutellaevent.util.exception.EventServiceException;
import springfox.documentation.swagger2.mappers.ModelMapperImpl;

@RestController
@RequestMapping("api/board")
@Api(tags = "게시판 API")
public class BoardApiController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ModelMapperImpl mapper = new ModelMapperImpl();
	
	@Autowired
	private BoardServiceImpl service;
	
	@PostMapping("/register")
	@ApiOperation(value = "글등록 - 등록하기 버튼 클릭 시 호출되는 API (DB에 저장)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ResponseVO<Boolean> post (@Valid @ModelAttribute BoardDto dto) throws EventServiceException {
		
		logger.debug(">>>>>>>>>>>>>> 게시판 DTO: {}", dto);
		
		try {
			service.create(dto.toEntity());
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return new ResponseVO<Boolean>(true, "등록 되었습니다.", "");
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "글목록 - 글목록 호출되는 API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ResponseVO<Boolean> get () throws EventServiceException {
		
		logger.debug(">>>>>>>>>>>>>> 게시판 목록");
		
		return new ResponseVO<Boolean>(true, "조회 되었습니다.", service.getList()); 
	}
	
	@GetMapping("/record")
	@ApiOperation(value = "선택 글 호출되는 API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ResponseVO<Boolean> getByIdx(@RequestParam(value = "idx") String idx) throws EventServiceException {
		
		logger.debug(">>>>>>>>>>>>>> 게시글 idx {}", idx);
		
		return new ResponseVO<Boolean>(true, "조회 완료", service.getById(Long.parseLong(idx)));
	}
	
	@PutMapping("/modify")
	@ApiOperation(value = "글수정 - 글수정시 호출되는 API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	 
	public ResponseVO<Boolean> put(@Valid @ModelAttribute BoardDto dto, @RequestParam(value = "idx") String idx) throws EventServiceException {
		
		logger.debug(">>>>>>>>>>>>>> 게시글 idx {}" );
		service.update(Long.parseLong(idx), dto.toEntity());
		return new ResponseVO<Boolean>(true, "수정 완료", "");
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value = "글수정 - 글수정시 호출되는 API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	 
	public ResponseVO<Boolean> delete(@RequestParam(value = "idx") String idx) throws EventServiceException {
		
		logger.debug(">>>>>>>>>>>>>> 게시글 idx {}" );
		service.delete(Long.parseLong(idx));
		return new ResponseVO<Boolean>(true, "삭제 완료", "");
	}
	
	@GetMapping("/pages")
	@ApiOperation(value = "게시판 페이지 - 게시판 페이지  호출되는 API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ResponseVO<Boolean> getPages(@RequestParam(value = "pNo") Integer pNo, @RequestParam(value = "size") Integer size) throws EventServiceException {
		
		
		logger.debug(">>>>>>>>>>>>>> 게시판 페이지{}{}", pNo, size);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		int totalPage = service.getPagesNum(pNo, size);
		
		hashmap.put("total", totalPage);
		hashmap.put("list", service.getPages(pNo, size));
		return new ResponseVO<Boolean>(true, "조회 되었습니다.", hashmap); 
	}
}
