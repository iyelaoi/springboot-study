package cn.wqz.study.springboot.swagger2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常处理器
 */
@ControllerAdvice
@Slf4j
public class MyValidException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handler(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = ex.getBindingResult();
            StringBuffer stringBuffer = new StringBuffer();
            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    //该格式仅仅作为response展示和log作用，前端应自己做校验
                    stringBuffer.append(fieldError.getObjectName() + "--" + fieldError.getDefaultMessage() + " ");
                }
            }
            log.error(stringBuffer.toString());
            return HttpStatus.BAD_REQUEST.value() + stringBuffer.toString();
        }
        e.printStackTrace();
        return e.getMessage();
    }


}
