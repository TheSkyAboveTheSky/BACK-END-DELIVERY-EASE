package com.project.isima.repositories;

import com.project.isima.entities.User;
import com.project.isima.enums.AccountStatus;
import com.project.isima.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void itShouldfindUserByEmail() {
        // given
        String email = "kabmouad274@gmail.com";
        User sender = new User(
                1L,
                "Mouad",
                "Kabouri",
                null,
                null,
                email,
                "1234",
                Role.SENDER,
                AccountStatus.ACTIVATED
        );

        underTest.save(sender);

        // when
        Optional<User> expected = underTest.findUserByEmail(email);

        // then
        assertThat(expected).isPresent();
        assertThat(expected.get().getEmail()).isEqualTo(email);
    }

}