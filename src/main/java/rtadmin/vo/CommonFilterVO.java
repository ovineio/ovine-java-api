package rtadmin.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class CommonFilterVO {

  /** 页数 */
  @Min(0)
  private Integer page;

  /** 每页长度 */
  @Max(value = 200, message = "size必须小于等于200")
  @Min(value = 1, message = "size必须大于1")
  private Integer size;

  /** 查询开始时间 */
  private String stime;

  /** 查询结束时间 */
  private String etime;

}