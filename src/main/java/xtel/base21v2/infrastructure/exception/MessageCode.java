package xtel.base21v2.infrastructure.exception;

public class MessageCode {

    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // TODO: MessageCode code Lỗi
    public static final String ACCOUNT_INACTIVE = "Account is not activated";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String GROUP_NOT_FOUND = "User group not found";
    public static final String PASSWORD_NOT_NULL = "Password cannot be empty";
    public static final String USER_NOT_NULL = "Username cannot be empty";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TOKEN_NOT_FOUND = "Invalid token";
    public static final String INCORRECT_PASSWORD = "Incorrect password";


    public static final String ACCOUNT_EXIST = "Account already existed";

    public static final String NEW_ANOTHER_OLD_PASSWORD = "The new password must be different from the old password";

    public static final String  BLOG_NOT_FOUNT = "Bài viết không tồn tại";

    public static class ServiceType {
        public static final String NOT_FOUND = "Không tìm thấy loại dịch vụ";
    }

    public static class ServicePlan {
        public static final String NOT_FOUND = "Không tìm thấy gói dịch vụ";
    }

}
