package org.complete.domain.member.oauth.user;

public enum RoleType {

    USER("ROLE_USER", "일반 사용자 권한"),
    ADMIN("ROLE_ADMIN", "관리자 권한");

    final String code;
    final String displayName;

    RoleType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
