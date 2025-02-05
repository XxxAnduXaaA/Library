package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface UserRepository extends JpaRepository<User,Long> {
}
