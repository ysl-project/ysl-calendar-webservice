package com.ysl.calendar.security;

import com.ysl.calendar.domain.users.CalendarUser;
import com.ysl.calendar.user.repository.UserAddRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

//    private final PasswordEncoder passwordEncoder;
    private final UserAddRepository userAddRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        CalendarUser calendarUser = userAddRepository.findById(userId);
        return createUserDetails(calendarUser); // @TODO : 에러처리
    }

    private UserDetails createUserDetails(CalendarUser calendarUser){
        return User.builder()
                .username(calendarUser.getId())
                .password(calendarUser.getPassword())
                .roles("USER")
                .build();
    }
}
