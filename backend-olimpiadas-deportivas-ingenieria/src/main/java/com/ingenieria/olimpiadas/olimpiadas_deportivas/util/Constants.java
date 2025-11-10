package com.ingenieria.olimpiadas.olimpiadas_deportivas.util;

public final class Constants {

    private Constants() {}

    // ===== Roles =====
    public static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
    public static final String ROL_ARBITRO       = "ARBITRO";
    public static final String ROL_JUGADOR       = "JUGADOR";

    public static final String ROLE_PREFIX = "ROLE_";

    public static String asSpringRole(String rolNombreBD) {
        if (rolNombreBD == null || rolNombreBD.isBlank()) {
            rolNombreBD = ROL_JUGADOR;
        }
        return ROLE_PREFIX + rolNombreBD.toUpperCase();
    }

    // ===== Permisos =====
    public static final String P_PARTIDOS_VER      = "Partidos_Ver";
    public static final String P_PARTIDOS_CREAR    = "Partidos_Crear";
    public static final String P_PARTIDOS_EDITAR   = "Partidos_Editar";
    public static final String P_PARTIDOS_ELIMINAR = "Partidos_Eliminar";

    public static final String P_TORNEOS_VER       = "Torneos_Ver";

    public static final String P_POSICIONES_VER    = "Posiciones_Ver";

    public static final String P_EQUIPOS_VER       = "Equipos_Ver";
    public static final String P_EQUIPOS_CREAR     = "Equipos_Crear";
    public static final String P_EQUIPOS_EDITAR    = "Equipos_Editar";
    public static final String P_EQUIPOS_ELIMINAR  = "Equipos_Eliminar";

    public static final String P_USUARIOS_EDITAR_ROL = "Usuarios_Editar_Rol";

    // ===== Fases =====
    public static final String FASE_GRUPOS_NOMBRE_LIKE = "Grupo";
    public static final String FASE_GRUPOS_NOMBRE_EXACT = "Fase de Grupos";
    public static final String FASE_CUARTOS_DE_FINAL_NOMBRE_EXACT = "Cuartos de Final";
    public static final String FASE_SEMIFINAL_NOMBRE_EXACT = "Semifinal";
    public static final String FASE_FINAL_NOMBRE_EXACT = "Final";

    // ===== Otros =====
    public static final String DOMINIO_PERMITIDO = "@udea.edu.co";
    public static final String REGEX_DOCUMENTO   = "^[0-9]{6,12}$";
    public static final String ZONA_BOGOTA       = "America/Bogota";
}
