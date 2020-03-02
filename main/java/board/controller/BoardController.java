package kr.co.nutellaevent.board.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.nutellaevent.board.dao.BoardEntity;
import kr.co.nutellaevent.board.dto.BoardDto;
import kr.co.nutellaevent.board.service.BoardService;
import kr.co.nutellaevent.util.ResponseVO;
import kr.co.nutellaevent.util.exception.EventServiceException;

@Controller
@RequestMapping("board")
public class BoardController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BoardService service;

	@GetMapping("")
	public ModelAndView index() {
		return new ModelAndView("board/index");
	}
	
	@GetMapping("/register")
	public ModelAndView getRegister() {
		return new ModelAndView("board/register");
	}
	
	@PostMapping("/register")
	@ApiOperation(value = "게시글 - 게시글  등록")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ModelAndView register(@Valid @ModelAttribute BoardDto dto) throws EventServiceException, IOException {
		logger.debug("------------postmapping----- {}");
//		new String(dto.getContent().getBytes("8859_1"));
		dto.setContent(new String(dto.getContent().getBytes("8859_1")));
		dto.setName(new String(dto.getName().getBytes("8859_1")));
		dto.setTitle(new String(dto.getTitle().getBytes("8859_1")));
		
		try {
			service.create(dto.toEntity());	
		} catch (Exception e) {
			logger.debug("try catch ---");
			logger.debug(e.getMessage());
		}
		
		return new ModelAndView("board/index");	
		
	}
	
	@GetMapping("/modify/{idx}")
	public ModelAndView getModify(@PathVariable String idx, Model model) throws EventServiceException {
		logger.debug("============== {}", idx);
		/*logger.debug("boardController------- {}", service.getById(Long.parseLong(idx)));*/
		Optional<BoardEntity> data = service.getById(Long.parseLong(idx));
		model.addAttribute("board", data);
		return new ModelAndView("board/modify");
	}
	
	@PostMapping("/modify/{idx}")
	@ApiOperation(value = "게시글 - 게시글  수정")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ModelAndView modify(@Valid @ModelAttribute BoardDto dto, @PathVariable String idx) throws EventServiceException, IOException {
		logger.debug("============== modify {}", idx);
		/*logger.debug("boardController------- {}", service.getById(Long.parseLong(idx)));*/
		dto.setContent(new String(dto.getContent().getBytes("8859_1")));
		dto.setName(new String(dto.getName().getBytes("8859_1")));
		dto.setTitle(new String(dto.getTitle().getBytes("8859_1")));
		service.update(Long.parseLong(idx), dto.toEntity());
		return new ModelAndView("board/index");
	}
	
	@GetMapping("/item/{idx}")
	@ApiOperation(value = "게시글 - 게시글  호출")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ModelAndView boarditem(@PathVariable String idx, Model model) throws EventServiceException {
		logger.debug("============== {}", idx);
		/*logger.debug("boardController------- {}", service.getById(Long.parseLong(idx)));*/
		Optional<BoardEntity> data = service.getById(Long.parseLong(idx));
		model.addAttribute("board", data);
		return new ModelAndView("board/item");
	}
	
	@GetMapping("/delete/{idx}")
	@ApiOperation(value = "게시글 삭제")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResponseVO.class), 
							@ApiResponse(code = 400, message = "BAD_REQUEST", response = EventServiceException.class),
							@ApiResponse(code = 500, message = "오류가 발생했습니다. 잠시 후 다시 시도해주세요.", response = Exception.class) })
	public ModelAndView delete (@PathVariable String idx) throws EventServiceException {
		logger.debug("============== delete {}", idx);
		/*logger.debug("boardController------- {}", service.getById(Long.parseLong(idx)));*/
		service.delete(Long.parseLong(idx));	
		return new ModelAndView("board/index");
	}
}
