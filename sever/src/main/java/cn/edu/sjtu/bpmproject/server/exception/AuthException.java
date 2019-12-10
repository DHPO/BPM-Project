package cn.edu.sjtu.bpmproject.server.exception;

import cn.edu.sjtu.bpmproject.server.enums.ResultStatus;
import cn.edu.sjtu.bpmproject.server.vo.ResultVO;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthException {

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ResultVO<String> authorizationException (){
        return new ResultVO<>(ResultStatus.FORBIDDEN,ResultStatus.getStatus(ResultStatus.FORBIDDEN));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultVO<String> incorrectCredentialsException (){
        return new ResultVO<>(ResultStatus.PASSWORD_ERROR,ResultStatus.getStatus(ResultStatus.PASSWORD_ERROR));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownAccountException.class)
    public ResultVO<String> unknownAccountException (){
        return new ResultVO<>(ResultStatus.USER_NOT_EXIST,ResultStatus.getStatus(ResultStatus.USER_NOT_EXIST));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LockedAccountException.class)
    public ResultVO<String> lockedAccountException (){
        return new ResultVO<>(ResultStatus.LOCKED_ACCOUNT,ResultStatus.getStatus(ResultStatus.LOCKED_ACCOUNT));
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVO<String> missingServletRequestParameterException (){
        return new ResultVO<>(ResultStatus.NOT_LOGIN,ResultStatus.getStatus(ResultStatus.NOT_LOGIN));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultVO<String> handleException(Exception e){
        e.printStackTrace();
        return new ResultVO<>(ResultStatus.SYSTEM_ERROR,ResultStatus.getStatus(ResultStatus.SYSTEM_ERROR));
    }
}
