package rtadmin.vo;


import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {
  public static Integer CODE_OK = 0;
  public static Integer CODE_ERROR = 1;

  public static final String DEFAULT_SUCCESS_MSG = "OK";
  public static final String DEFAULT_ERROR_MSG = "ERROR";


  /** 返回 code 值 */
  @NotEmpty
  private Integer code;

  /** 错误信息 */
  private String msg;

  /** 返回参数 data */
  private T data;

  public static ResultVO success(Object object) {
    ResultVO resultVO = new ResultVO();
    resultVO.setData(object);
    resultVO.setCode(CODE_OK);
    resultVO.setMsg(DEFAULT_SUCCESS_MSG);
    return resultVO;
  }

  public static ResultVO success() {
    return success(null);
  }

  public static ResultVO error(String message) {
    ResultVO resultVO = new ResultVO();
    resultVO.setCode(CODE_ERROR);
    resultVO.setMsg(message);
    return resultVO;
  }
}