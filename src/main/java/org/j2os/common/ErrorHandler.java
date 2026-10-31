//package org.j2os.common;
//
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.dao.InvalidDataAccessResourceUsageException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//@Slf4j
//public class ErrorHandler {
//
/// /    @ExceptionHandler(Exception.class)
/// /    public void onException(Exception exception, HttpServletResponse response) {
/// /        int errorCode = 800;
/// /        if (exception instanceof SQLException) {
/// /            errorCode = 700;
/// /        } else if (exception instanceof ArithmeticException) {
/// /            errorCode = 701;
/// /        } else if (exception instanceof ClassNotFoundException) {
/// /            errorCode = 702;
/// /        } else if (exception instanceof MethodArgumentNotValidException) {
/// /            errorCode = 703;
/// /        } else {
/// /            errorCode = 900;
/// /        }
/// /
/// /        log.error(String.valueOf(exception.getStackTrace()[0]).concat("=>".concat(exception.getClass().toString())), exception);
/// /        response.setStatus(errorCode);
/// /    }
//
//
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> onException(Exception exception) {
//
//        // ۱. ثبت لاگ به شکل اصولی (نیازی به گرفتن دسته‌جمعی استک‌تریس به صورت دستی نیست)
//        log.error("An error occurred: ", exception);
//
//        Map<String, Object> body = new HashMap<>();
//        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // پیش‌فرض ۵۰۰
//        int internalErrorCode = 0;// 900; // کد خطای اختصاصی پروژه شما
//
//        // ۲. تفکیک خطاها به شیوه استاندارد
//        if (exception instanceof SQLException || exception instanceof InvalidDataAccessResourceUsageException) {
//            status = HttpStatus.BAD_REQUEST; // یا INTERNAL_SERVER_ERROR
//            internalErrorCode = 700;
//            body.put("message", "Database error occurred.");
//        } else if (exception instanceof ArithmeticException) {
//            status = HttpStatus.BAD_REQUEST;
//            internalErrorCode = 701;
//            body.put("message", "Mathematical error occurred.");
//        } else if (exception instanceof ClassNotFoundException) {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            internalErrorCode = 702;
//            body.put("message", "Class not found on server.");
//        } else if (exception instanceof MethodArgumentNotValidException) {
//            status = HttpStatus.BAD_REQUEST;
//            internalErrorCode = 703;
//            body.put("message", "Validation failed for arguments.");
//        } else if (exception instanceof NoResourceFoundException) {
//            log.info("Rest API Invoked...");
//        } else {
//            body.put("message", "An unexpected error occurred.");
//        }
//
//        // ۳. پر کردن بدنه پاسخ برای فرانت‌اند
//        body.put("success", false);
//        body.put("internalCode", internalErrorCode);
//        body.put("developerMessage", exception.getMessage()); // پیغام اصلی خطا
//
//        // بازگرداندن پاسخ با وضعیت استاندارد HTTP و بدنه JSON
//        return new ResponseEntity<>(body, status);
//    }
//}
