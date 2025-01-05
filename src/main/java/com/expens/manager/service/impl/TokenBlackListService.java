package com.expens.manager.service.impl;


import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 *  This class is used to manage the token black list
 */
@Service
public class TokenBlackListService {

    public Set<String> tokenBlackList = new HashSet<>();

    /**
     * This method is used to add the token to the block list
     * @param token
     * @return void
     */
    public void addTokenToBlackList(String token){
        tokenBlackList.add( token );
    }

    /**
     * This method is used to check if the token is block listed
     * @param token
     * @return boolean
     */
    public boolean isTokenIsBlockListed(String token){
        return tokenBlackList.contains( token );
    }

}
