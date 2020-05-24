package com.igroupes.rtadmin.vo;

import com.igroupes.rtadmin.enums.ResultEnum;
import lombok.Data;

/**
 * 最终返回对象
 *
 * @param <T>
 */
@Data
public class ResultVO<T> {
    public static int CODE_OK = 0;
    private Integer code;
    private String msg;
    private T data;

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(CODE_OK);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }


    public static ResultVO error(String msg,Integer code) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static <T> ResultVO<T> error(ResultEnum resultEnum) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }



}