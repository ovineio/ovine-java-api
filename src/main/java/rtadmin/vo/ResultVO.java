package rtadmin.vo;


import lombok.Data;

@Data
public class ResultVO<T> {
  public static Integer CODE_OK = 0;
  public static Integer CODE_ERROR = 1;

  public static final String DEFAULT_SUCCESS_MSG = "OK";
  public static final String DEFAULT_ERROR_MSG = "ERROR";

  private Integer code;
  private String msg;
  private T data;

  public static ResultVO success(Object object) {
    ResultVO resultVO = new ResultVO();
    resultVO.setData(object);
    resultVO.setCode(CODE_OK);
    resultVO.setMsg(DEFAULT_SUCCESS_MSG);
    return resultVO;
  }
}