package kr.co.nutellaevent.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import kr.co.nutellaevent.board.dao.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	
	@Modifying
	@Transactional
	@Query("UPDATE BoardEntity SET name = :name, title = :title, content = :content WHERE idx = :idx")
	void update(@Param("idx") Long idx, @Param("name") String name, @Param("title") String title, @Param("content") String content);
}
