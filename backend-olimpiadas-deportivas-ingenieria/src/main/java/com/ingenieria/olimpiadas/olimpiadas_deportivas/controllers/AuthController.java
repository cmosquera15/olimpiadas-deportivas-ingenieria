package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.AuthDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.JwtTokenProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${google.client.id}")
    private String googleClientId;

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
        String googleToken = payload.get("token");

        try {
            GoogleIdToken idToken = verifyGoogleToken(googleToken);

            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }

            GoogleIdToken.Payload payloadGoogle = idToken.getPayload();
            String correo = payloadGoogle.getEmail();
            String nombre = (String) payloadGoogle.get("name");

            if (!correo.endsWith("@udea.edu.co")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo usuarios de @udea.edu.co pueden ingresar");
            }

            String jwtToken = jwtTokenProvider.generateToken(correo);
            return ResponseEntity.ok(new AuthDTO(jwtToken, nombre, correo));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error al verificar el token");
        }
    }

    private GoogleIdToken verifyGoogleToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        return verifier.verify(token);
    }
}
