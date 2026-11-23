package com.koithe.mrp.exception;

import com.koithe.mrp.dto.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Lỗi không tìm thấy dữ liệu (404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.loi(404, ex.getMessage(), null));
    }

    // 2. Lỗi trùng lặp dữ liệu nghiệp vụ (409 Conflict)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.loi(409, ex.getMessage(), null));
    }

    // 3. Lỗi Validation dữ liệu đầu vào từ @Valid (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> chiTietLoi = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            chiTietLoi.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.loi(400, "Dữ liệu đầu vào không hợp lệ", chiTietLoi));
    }

    // 4. Lỗi tham số nghiệp vụ không hợp lệ (400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.loi(400, ex.getMessage(), null));
    }

    // 5. Lỗi vi phạm ràng buộc khóa ngoại / CHECK từ PostgreSQL (409 Conflict)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.loi(409,
                        "Không thể thực hiện thao tác do dữ liệu đang được liên kết với chứng từ/công thức khác hoặc vi phạm ràng buộc hệ thống.",
                        null));
    }

    // 6. Các lỗi hệ thống chưa xác định khác (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.loi(500, "Lỗi hệ thống: " + ex.getMessage(), null));
    }
}