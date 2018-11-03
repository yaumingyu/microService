package com.imooc.users.test;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * Created by Administrator on 2018/10/3 0003.
 */
public class Test {


    public static void main(String[] args){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
        String akasjs = jws+"888888";
        try {

            Jwts.parser().setSigningKey(key).parseClaimsJws(akasjs);
                System.out.print(true);
            //OK, we can trust this JWT

        } catch (JwtException e) {
            e.printStackTrace();
            System.out.print(false);
            //don't trust the JWT!
        }
    }
}
