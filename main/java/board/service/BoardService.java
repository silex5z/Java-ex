package kr.co.nutellaevent.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.nutellaevent.board.dao.BoardEntity;

@Service
public interface BoardService {
	List<BoardEntity> getList();
	Optional<BoardEntity> getById(Long idx);
	BoardEntity create(BoardEntity dao) throws Exception;
	void update(Long idx, BoardEntity dao);
	String delete(Long idx);
	List<BoardEntity> getPages(Integer pNo, Integer size);
	int getPagesNum(Integer pNo, Integer size);
}
