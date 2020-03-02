package kr.co.nutellaevent.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiParam;
import kr.co.nutellaevent.board.dao.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	@ApiParam(value = "성명 (한글,영문으로 조합)")
	@NotBlank(message = "이름은 한글 또는 영문으로 기입해주세요. (최대 20자 이내)")
	@Size(max = 20, message = "이름은 한글 또는 영문으로 기입해주세요. (최대 20자 이내)")
//	@Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 한글 또는 영문으로 기입해주세요. (최대 20자 이내)")
	public String name;
	
	@ApiParam(value = "글제목")
	@NotBlank(message = "제목을 작성해주세요.")
	@Size(max = 20, message = "20자 이내로 작성해주세요.")
	public String title;
	
	@ApiParam(value = "글내용")
	@NotBlank(message = "내용을 작성해주세요.")
	@Size(max = 400, message = "400자 이내로 작성해주세요.")
	public String content;
	
	public BoardEntity toEntity() {
		return BoardEntity.builder()
							.name(name)
							.title(title)
							.content(content)
							.build();
	}
}
