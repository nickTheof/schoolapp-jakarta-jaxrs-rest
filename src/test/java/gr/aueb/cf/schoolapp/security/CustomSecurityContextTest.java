package gr.aueb.cf.schoolapp.security;

import gr.aueb.cf.schoolapp.core.enums.Role;
import gr.aueb.cf.schoolapp.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomSecurityContextTest {

    @Test
    void isUserInRole_returnsTrueForMatchingRole() {
        User user = new User();
        user.setUsername("admin@mail.com");
        user.setRole(Role.ADMIN);

        CustomSecurityContext context = new CustomSecurityContext(user);

        assertTrue(context.isUserInRole("ADMIN"));
        assertTrue(context.isUserInRole("admin"));
        assertTrue(context.isUserInRole("AdMiN"));
    }

    @Test
    void isUserInRole_returnsFalseForNonMatchingRole() {
        User user = new User();
        user.setUsername("editor@mail.com");
        user.setRole(Role.EDITOR);

        CustomSecurityContext context = new CustomSecurityContext(user);

        assertFalse(context.isUserInRole("ADMIN"));
        assertFalse(context.isUserInRole("READER"));
    }

    @Test
    void isUserInRole_handlesNullUserSafely() {
        CustomSecurityContext context = new CustomSecurityContext(null);
        assertFalse(context.isUserInRole("ADMIN"));
    }

    @Test
    void isUserInRole_handlesNullRoleSafely() {
        User user = new User();
        user.setUsername("unknown@mail.com");
        user.setRole(null);

        CustomSecurityContext context = new CustomSecurityContext(user);
        assertFalse(context.isUserInRole("ADMIN"));
    }
}
