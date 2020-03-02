package kr.co.nutellaevent.board.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import kr.co.nutellaevent.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="BOARD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long idx;
	
	@Column(columnDefinition="NVARCHAR(6)", nullable = false)
	public String name;
	
	@Column(columnDefinition="NVARCHAR(400)", nullable = false)
	public String content;
	
	@Column(columnDefinition="NVARCHAR(20)", nullable = false)
	public String title;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	public Date regDate;
	
	public BoardDto toDto() {
		return BoardDto.builder()
						.name(name)
						.title(title)
						.content(content)
						.build();
	}
	
}
