package kr.co.nutellaevent.board.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import kr.co.nutellaevent.board.dao.BoardEntity;
import kr.co.nutellaevent.board.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository repository;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<BoardEntity> getList() {
		
		return repository.findAll();
	}

	@Override
	public Optional<BoardEntity> getById(Long idx) {
		// TODO Auto-generated method stub
		return repository.findById(idx);
//		PageRequest request = new PageRequest(1, 10, new Sort(Direction.DESC, "CreateDate"));
//		Page<BoardEntity> boards = repository.findAll(request);
//		boards.map(new Converter<>)
		
	}
	
	@Override
	public List<BoardEntity> getPages(Integer pNo, Integer size) {
		PageRequest pageRequest = PageRequest.of(pNo, size);
		
		return repository.findAll(pageRequest).getContent();
	}

	@Override
	public int getPagesNum(Integer pNo, Integer size) {
		PageRequest pageRequest = PageRequest.of(pNo, size);
		int totalPageNum = repository.findAll(pageRequest).getTotalPages();
		logger.debug("><><><><>< totalPageNum {}", totalPageNum);
		return totalPageNum;
	}

	@Override
	public void update(Long idx, BoardEntity dao) {
		
		logger.debug("update {} {}", idx, dao );
		// TODO Auto-generated method stub
		BoardEntity b = repository.getOne(idx);
		b.setName(dao.getName());
		b.setTitle(dao.getTitle());
		b.setContent(dao.getContent());
//		repository.save(b);
		repository.update(idx, b.getName(), b.getTitle(), b.getContent());
		
	}

	@Override
	public String delete(Long idx) {
		// TODO Auto-generated method stub
		repository.deleteById(idx);
		return null;
	}

	@Override
	public BoardEntity create(BoardEntity dao) throws DataIntegrityViolationException{
		// TODO Auto-generated method stub
		return repository.save(dao);
	}
	
	
}
