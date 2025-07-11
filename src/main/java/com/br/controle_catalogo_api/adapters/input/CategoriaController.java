package com.br.controle_catalogo_api.adapters.input;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.domain.port.CategoriaServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/categoria")
@Tag(name = "Categoria", description = "Gerenciamento de categorias")
public class CategoriaController {

    private static final Logger logger = Logger.getLogger(CategoriaController.class.getName());
    private final CategoriaServicePort categoriaServicePort;

    public CategoriaController(CategoriaServicePort categoriaServicePort) {
        this.categoriaServicePort = categoriaServicePort;
    }

    @Operation(summary = "Inserir nova categoria",
            description = "Cria uma nova categoria no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria inserida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponse> insert(@RequestBody CadastraCategoriaRequest categoriaRequest) {
        logger.info("Recebida requisicao para inserir categoria com ID: " + categoriaRequest.getCategoriaId());
        CategoriaResponse response = categoriaServicePort.insert(categoriaRequest);
        logger.info("Categoria inserida com sucesso. ID: " + response.getCategoriaId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Buscar todas as categorias",
            description = "Retorna uma lista de todas as categorias cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getAll() {
        logger.info("Recebida requisicao para buscar todas as categorias");
        List<CategoriaResponse> response = categoriaServicePort.getAll();
        logger.info("Busca de categorias concluída. Total encontrado: " + response.size());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(@PathVariable("id") String id, @RequestBody AtualizaCategoriaRequest categoriaRequest) {
        logger.info("Recebida requisicao para atualizar categoria com ID: " + id);
        CategoriaResponse response = categoriaServicePort.update(id, categoriaRequest);
        logger.info("Categoria atualizada com sucesso. ID: " + response.getCategoriaId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Deletar categoria",
            description = "Remove uma categoria do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        logger.info("Recebida requisicao para deletar categoria com ID: " + id);
        categoriaServicePort.delete(id);
        logger.info("Categoria com ID: " + id + " deletada com sucesso (lógica de deleção não implementada)");
        return ResponseEntity.noContent().build();
    }
}
