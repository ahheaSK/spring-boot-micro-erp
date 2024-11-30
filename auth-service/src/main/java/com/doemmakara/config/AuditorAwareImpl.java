package com.doemmakara.config;


import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public @NonNull Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return Optional.of(authentication.getName());
		}
		return Optional.empty();
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		return Optional.ofNullable(username);
	}

}
