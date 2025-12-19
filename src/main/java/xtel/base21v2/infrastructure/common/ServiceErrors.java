package xtel.base21v2.infrastructure.common;

public class ServiceErrors {
    public static final ServiceError SUCCESS = new ServiceError(0, "Thành công");
    public static final ServiceError ERROR = new ServiceError(99, "Lỗi hệ thống");
    public static final ServiceError BAD_REQUEST = new ServiceError(400, "Yêu cầu không hợp lệ");
    public static final ServiceError FORBIDDEN = new ServiceError(403, "Bạn chưa có quyền truy cập vào hệ thống");
    public static final ServiceError UNAUTHORIZED = new ServiceError(1, "Phiên đăng nhập không tồn tại");
    public static final ServiceError USERNAME_OR_PASSWORD_NOT_CORRECT = new ServiceError(400, "Tài khoản hoặc mật khẩu không chính xác.");
    public static final ServiceError USERNAME_NOT_VALID = new ServiceError(400, "Tài khoản không đúng định dạng email hoặc số điện thoại.");
    public static final ServiceError ACCOUNT_INACTIVE = new ServiceError(902, "Tài khoản không được kích hoạt");
    public static final ServiceError NOT_FOUND =  new ServiceError(404, "Tài nguyên không tồn tại");
}
