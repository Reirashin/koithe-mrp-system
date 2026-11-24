package com.koithe.mrp.exception;

import com.koithe.mrp.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Lỗi không tìm thấy dữ liệu nghiệp vụ (404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    // 2. Lỗi trùng lặp dữ liệu nghiệp vụ (409 Conflict)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicate(DuplicateResourceException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    // 3. Lỗi Validation dữ liệu đầu vào từ @Valid trên RequestBody DTO (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> chiTietLoi = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            chiTietLoi.merge(error.getField(),
                    error.getDefaultMessage() != null ? error.getDefaultMessage() : "Không hợp lệ",
                    (cu, moi) -> cu + "; " + moi);
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            chiTietLoi.merge(error.getObjectName(),
                    error.getDefaultMessage() != null ? error.getDefaultMessage() : "Không hợp lệ",
                    (cu, moi) -> cu + "; " + moi);
        }
        return buildError(HttpStatus.BAD_REQUEST, "Dữ liệu đầu vào không hợp lệ", chiTietLoi);
    }

    // 4. Lỗi Validation trực tiếp trên @RequestParam / @PathVariable (400 Bad Request)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodValidation(HandlerMethodValidationException ex) {
        Map<String, String> chiTietLoi = new LinkedHashMap<>();
        ex.getParameterValidationResults().forEach(result -> {
            String paramName = result.getMethodParameter().getParameterName();
            String key = paramName != null ? paramName : "parameter";
            result.getResolvableErrors().forEach(err -> {
                String msg = err.getDefaultMessage() != null ? err.getDefaultMessage() : "Không hợp lệ";
                chiTietLoi.merge(key, msg, (cu, moi) -> cu + "; " + moi);
            });
        });
        return buildError(HttpStatus.BAD_REQUEST, "Tham số đầu vào không hợp lệ", chiTietLoi);
    }

    // 5. Lỗi tham số nghiệp vụ không hợp lệ (400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // 6. Lỗi gọi sai đường dẫn API không tồn tại (404 Not Found)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoResourceFound(NoResourceFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND,
                "Đường dẫn API không tồn tại: " + ex.getResourcePath(), null);
    }

    // 7. Lỗi gọi sai HTTP Method (405 Method Not Allowed)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildError(HttpStatus.METHOD_NOT_ALLOWED,
                "Phương thức HTTP " + ex.getMethod() + " không được hỗ trợ cho đường dẫn này", null);
    }

    // 8. Lỗi gửi JSON sai cú pháp hoặc sai kiểu dữ liệu (400 Bad Request)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnreadableMessage(HttpMessageNotReadableException ex) {
        return buildError(HttpStatus.BAD_REQUEST,
                "Dữ liệu JSON gửi lên không hợp lệ hoặc sai kiểu dữ liệu", null);
    }

    // 9. Lỗi truyền sai kiểu dữ liệu trên URL param / path variable (400 Bad Request)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildError(HttpStatus.BAD_REQUEST,
                "Tham số '" + ex.getName() + "' có giá trị không đúng định dạng", null);
    }

    // 10. Lỗi thiếu query param bắt buộc (400 Bad Request)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParam(MissingServletRequestParameterException ex) {
        return buildError(HttpStatus.BAD_REQUEST,
                "Thiếu tham số bắt buộc: " + ex.getParameterName(), null);
    }

    // 11. Lỗi vi phạm ràng buộc khóa ngoại / CHECK / UNIQUE từ PostgreSQL (409 Conflict)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Vi phạm ràng buộc dữ liệu PostgreSQL: {}", ex.getMostSpecificCause().getMessage());
        return buildError(HttpStatus.CONFLICT,
                "Không thể thực hiện thao tác do dữ liệu đang được liên kết với chứng từ/công thức khác hoặc vi phạm ràng buộc hệ thống.",
                null);
    }

    // 12. Các lỗi hệ thống chưa xác định khác (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        log.error("Lỗi hệ thống không xác định: ", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau.", null);
    }

    private ResponseEntity<ApiResponse<Object>> buildError(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status)
                .body(ApiResponse.loi(status.value(), message, data));
    }
}