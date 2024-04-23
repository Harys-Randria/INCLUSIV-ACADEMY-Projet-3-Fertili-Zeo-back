package com.fertilizeo.service.impl;

import com.fertilizeo.entity.Compte;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CompteRepository compteRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws AuthenticationException {
        Compte compte = compteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

        if (!compte.isEnable()){
            throw new AccountExpiredException("Ce compte n'est pas activé");
        }
        return UserDetailsImpl.build(compte);
    }

}
