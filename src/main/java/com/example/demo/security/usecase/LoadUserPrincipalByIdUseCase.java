package com.example.demo.security.usecase;

import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoadUserPrincipalByIdUseCase {

    UserDetails execute(UUID userId);
}
