package br.com.microsservice.auth.security.filter;

import br.com.microsservice.auth.endpoint.model.ApplicationUser;
import br.com.microsservice.auth.endpoint.property.JwtConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response){
        log.info("Attempting authentication. . .");
        ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);

        if(applicationUser == null) {
            throw new UsernameNotFoundException("Unable to retriever the username or password");
        }
        log.info("Creating the authentication object for the user '{}' and calling UserDetailsServiceImpl loadUserByUsername", applicationUser.getUsername());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
        authenticationToken.setDetails(applicationUser);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        log.info("Authentication was successful for the '{}', generating JWE token", auth.getName());

        SignedJWT sinSignedJWT = createSinSignedJWT(auth);

        String encryptToken = encryptToken(sinSignedJWT);

        log.info("Token generated successfully, adding it to the response header");

        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfiguration.getHeader().getName());

        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + encryptToken);
    }

    @SneakyThrows
    private SignedJWT createSinSignedJWT(Authentication auth) {
        log.info("Starting to create the signed JWT");
        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();

        JWTClaimsSet jwtClaimsSet = jwtClaimsSet(auth, applicationUser);

        KeyPair rsaKeys = geneKeyPair();

        log.info("Building JWK from the RSA Keys");

        JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic()).keyID(UUID.randomUUID().toString()).build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .jwk(jwk).type(JOSEObjectType.JWT).build(), jwtClaimsSet);

        log.info("Signing the token with the private RSA key");

        RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());
        signedJWT.sign(signer);

        log.info("Serialized token '{}'", signedJWT.serialize());
        return signedJWT;
    }

    private JWTClaimsSet jwtClaimsSet(Authentication auth, ApplicationUser applicationUser) {
        log.info("Creating the JWTClaimsSet Object for '{}'", applicationUser);

        return new JWTClaimsSet.Builder().subject(applicationUser.getUsername())
                .claim("authorities", auth.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .issuer("http://br.com.microsservice")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000L)))
                .build();
    }

    @SneakyThrows
    private KeyPair geneKeyPair() {
        log.info("Generating RSA 2048 bits Keys");

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        return generator.genKeyPair();
    }

    private String encryptToken(SignedJWT signedJWT) throws JOSEException {
        log.info("Starting the encryptToken method");

        DirectEncrypter directDecrypter = new DirectEncrypter(jwtConfiguration.getPrivateKey().getBytes());

        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
                .contentType("JWT").build(), new Payload(signedJWT));
        log.info("Encrypting token with system's private key");

        jweObject.encrypt(directDecrypter);

        log.info("Token encrypted");

        return jweObject.serialize();
    }
}
