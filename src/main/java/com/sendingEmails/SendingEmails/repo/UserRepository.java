package com.sendingEmails.SendingEmails.repo;

import com.sendingEmails.SendingEmails.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.appointment = :nextDay")
    List<User> findAllByAppointmentNextDay(@Param("nextDay") LocalDate nextDay);

    List<User> findByName(String name);

    @Query("SELECT u FROM User u WHERE u.appointment < :oneWeekAgo")
    List<User> findUsersWithOldAppointments(@Param("oneWeekAgo") LocalDate oneWeekAgo);
}
