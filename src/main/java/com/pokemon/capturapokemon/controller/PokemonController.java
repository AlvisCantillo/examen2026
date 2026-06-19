package com.pokemon.capturapokemon.controller;

import com.pokemon.capturapokemon.dto.*;
import com.pokemon.capturapokemon.model.*;
import com.pokemon.capturapokemon.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Listar Pokémon por tipo", description = "Permite listar todos los pokémones de un tipo registrado en el sistema usando el UUID del tipo")
    @GetMapping("/pokemons/tipo")
    public ResponseEntity<List<PokemonDto>> getPokemonsByTipo(
            @Parameter(description = "UUID del tipo de Pokémon") @RequestParam String uuid) {
        List<PokemonDto> result = pokemonService.getPokemonsByTipoUuid(uuid);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Registrar un Pokémon", description = "Permite registrar un nuevo Pokémon en el sistema")
    @PostMapping("/pokemons")
    public ResponseEntity<PokemonDto> createPokemon(
            @Parameter(description = "Datos del Pokémon a registrar") @RequestBody CreatePokemonRequest request) {
        PokemonDto created = pokemonService.createPokemon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar Pokémon de un entrenador", description = "Permite listar todos los pokémones capturados por un entrenador")
    @GetMapping("/entrenador/{uuid}/pokemon")
    public ResponseEntity<List<PokemonDto>> getPokemonsByEntrenador(
            @Parameter(description = "UUID del entrenador") @PathVariable String uuid) {
        try {
            List<PokemonDto> result = entrenadorService.getPokemonsByEntrenadorUuid(uuid);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Agregar Pokémon a un entrenador", description = "Permite agregar una lista de pokémones a un entrenador usando sus UUIDs")
    @PostMapping("/entrenador/{uuid}/pokemon")
    public ResponseEntity<List<PokemonDto>> addPokemonsToEntrenador(
            @Parameter(description = "UUID del entrenador") @PathVariable String uuid,
            @Parameter(description = "Lista de UUIDs de Pokémon a agregar") @RequestBody AddPokemonsToTrainerRequest request) {
        try {
            List<PokemonDto> result = entrenadorService.addPokemonsToEntrenador(uuid, request.pokemonUuids());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
