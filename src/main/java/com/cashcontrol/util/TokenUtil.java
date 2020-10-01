package com.cashcontrol.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cashcontrol.dto.TokenPair;
import com.cashcontrol.entity.UserToken;
import com.cashcontrol.entity.Users;
import com.cashcontrol.enums.AppPropertiesEnum;
import com.cashcontrol.enums.TokenType;
import com.cashcontrol.exception.TokenException;
import com.cashcontrol.repository.UserTokenRepository;
import com.cashcontrol.repository.UsersRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil implements Serializable {
	private static final long serialVersionUID = -8038644516191228173L;

	@Autowired
	private Environment environment;

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private UsersRepository usersRepository;

	// retrieve username from jwt token
	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject); // Method reference from java 1.8 , need to read
	}

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuer); // Method reference from java 1.8 , need to read
	}
	
	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(environment.getProperty(AppPropertiesEnum.JWT_TOKEN_SECRET_KEY.getPropName()))
				.parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(long userId, String username) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userId + ",",username);
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject, String username) {
		return Jwts.builder().setClaims(claims).setIssuer(username).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(
						environment.getProperty(AppPropertiesEnum.ACCESS_TOKEN_EXPIRY_IN_SECS_KEY.getPropName()))
						* 1000))
				.signWith(SignatureAlgorithm.HS512,
						environment.getProperty(AppPropertiesEnum.JWT_TOKEN_SECRET_KEY.getPropName()))
				.compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) throws TokenException {
		final String username = getUserNameFromToken(token);

		return (username.equals(userDetails.getUsername() + "") && !isTokenExpired(token));
	}

	// creating auth token from refresh token
	public TokenPair reGenerateRefreshAccessToken(String refreshToken) throws TokenException {

		Optional<UserToken> userTokenOptional = userTokenRepository.findByTokenAndTokenType(refreshToken,
				TokenType.REFRESH_TOKEN.getTokenTypeId());

		if (userTokenOptional.isPresent()) {
			// get before method
			if (new Date().getTime() - userTokenOptional.get().getCreatedOn().getTime() < Long.parseLong(
					environment.getProperty(AppPropertiesEnum.REFRESH_TOKEN_EXPIRY_IN_SECS_KEY.getPropName())) * 1000) {
				userTokenRepository.deleteByuseridAndTokenType(userTokenOptional.get().getUserid(),
						TokenType.AUTHORIZATION_TOKEN.getTokenTypeId());
				String accessToken = generateToken(userTokenOptional.get().getUserid(),userTokenOptional.get().getUser().getUsername());
				UserToken userToken = new UserToken();
				userToken.setUserid(userTokenOptional.get().getUserid());
				userToken.setCreatedOn(new Date());
				userToken.setToken(accessToken);
				userToken.setTokenType(TokenType.AUTHORIZATION_TOKEN.getTokenTypeId());
				userTokenRepository.save(userToken);
				return new TokenPair(accessToken, refreshToken);
			} else {
				return null;
			}
		}
		throw new TokenException("No Token");
	}

	public TokenPair createRefreshAndAccessToken(Users user) {
		String refreshToken = RandomStringUtils.randomAlphanumeric(128);
		UserToken rToken = new UserToken();
		rToken.setTokenType(TokenType.REFRESH_TOKEN.getTokenTypeId());
		rToken.setToken(refreshToken);
		rToken.setCreatedOn(new Date());
		rToken.setUserid(user.getUserid());
		rToken.setUser(user);
		userTokenRepository.save(rToken);

		String accessToken = generateToken(user.getUserid(),user.getUsername());
		UserToken acessToken = new UserToken();
		acessToken.setTokenType(TokenType.AUTHORIZATION_TOKEN.getTokenTypeId());
		acessToken.setToken(accessToken);
		acessToken.setCreatedOn(new Date());
		acessToken.setUserid(user.getUserid());
		userTokenRepository.save(acessToken);
		return new TokenPair(accessToken, refreshToken);

	}

	public boolean checkIfValidUser(String token, long userId) {
		return getUserIdFromToken(token).equals(userId + "");
	}

	public Optional<Users> getUserFromToken() {
		return (Optional<Users>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
