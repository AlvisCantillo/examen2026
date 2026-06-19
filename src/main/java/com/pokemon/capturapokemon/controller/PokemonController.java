package com.pokemon.capturapokemon.controller;

import com.pokemon.capturapokemon.dto.*;
import com.pokemon.capturapokemon.model.*;
import com.pokemon.capturapokemon.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Pokemon API", description = "API para gestión de Pokémon y Entrenadores")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private EntrenadorService entrenadorService;

    @Operation(summary = "Crear datos de prueba", description = "Genera datos iniciales: Pueblo Paleta, tipo Eléctrico, Pikachu y Ash Ketchum")
    @GetMapping("/test/setup")
    public String setupTestData() {
        // 1. Crear Pueblo
        Pueblo pueblo = Pueblo.builder()
                .nombre("Pueblo Paleta")
                .uuid(UUID.randomUUID().toString())
                .build();
        pueblo = entrenadorService.savePueblo(pueblo);

        // 2. Crear Tipo Pokemon
        TipoPokemon tipoElectrico = TipoPokemon.builder()
                .descripcion("Eléctrico")
                .uuid(UUID.randomUUID().toString())
                .build();
        tipoElectrico = pokemonService.saveTipoPokemon(tipoElectrico);

        // 3. Crear Pokemon
        Pokemon pikachu = Pokemon.builder()
                .nombre("Pikachu")
                .descripcion("Pokemon ratón de tipo eléctrico.")
                .tipoPokemon(tipoElectrico)
                .fechaDescubrimiento(LocalDate.of(1996, 2, 27))
                .generacion(1)
                .uuid(UUID.randomUUID().toString())
                .build();
        pikachu = pokemonService.savePokemon(pikachu);

        // 4. Crear Entrenador
        Entrenador ash = Entrenador.builder()
                .nombre("Ash")
                .apellido("Ketchum")
                .fechaNacimiento(LocalDate.of(1997, 5, 22))
                .fechaVinculacion(LocalDate.now())
                .pueblo(pueblo)
                .uuid(UUID.randomUUID().toString())
                .email("ash@gmail.com")
                .pokemons(new ArrayList<>(Collections.singletonList(pikachu)))
                .build();
        entrenadorService.saveEntrenador(ash);

        return "¡Datos de prueba creados exitosamente! Se creó a Ash Ketchum en Pueblo Paleta con su Pikachu.";
    }

    @Operation(summary = "Listar todos los Pokémon", description = "Retorna la lista completa de Pokémon registrados")
    @GetMapping("/pokemons")
    public List<PokemonDto> getPokemons() {
        return pokemonService.getAllPokemons();
    }

    @Operation(summary = "Listar todos los Entrenadores", description = "Retorna la lista completa de entrenadores con sus pokémon capturados")
    @GetMapping("/entrenadores")
    public List<EntrenadorDto> getEntrenadores() {
        return entrenadorService.getAllEntrenadores();
    }

    @Operation(summary = "Login de Entrenador", description = "Permite obtener el UUID de un usuario registrado en el sistema a partir de su email")
    @PostMapping("/entrenador/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return entrenadorService.getUuidByEmail(request.email())
                .map(uuid -> ResponseEntity.ok(new LoginResponse(uuid)))
                .orElse(ResponseEntity.notFound().build());
    }
}
