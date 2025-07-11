package com.br.controle_catalogo_api.adapters.input;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.domain.port.ProdutoServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto", description = "Gerenciamento de produtos")
public class ProdutoController {

    private static final Logger logger = Logger.getLogger(ProdutoController.class.getName());
    private final ProdutoServicePort produtoServicePort;

    public ProdutoController(ProdutoServicePort produtoServicePort) {
        this.produtoServicePort = produtoServicePort;
    }

    @Operation(summary = "Inserir novo produto",
            description = "Cria um novo produto no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse> insert(@RequestBody CadastraProdutoRequest produtoRequest) {
        logger.info("Recebida requisição para inserir produto com ID: " + produtoRequest.getProdutoId());
        ProdutoResponse response = produtoServicePort.insert(produtoRequest);
        logger.info("Produto inserido com sucesso. ID: " + response.getProdutoId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Buscar todos os produtos",
            description = "Retorna uma lista de todos os produtos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAll() {
        logger.info("Recebida requisição para buscar todos os produtos");
        List<ProdutoResponse> response = produtoServicePort.getAll();
        logger.info("Busca de produtos concluída. Total encontrado: " + response.size());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable("id") String id, @RequestBody AtualizaProdutoRequest produtoRequest) {
        logger.info("Recebida requisição para atualizar produto com ID: " + id);
        ProdutoResponse response = produtoServicePort.update(id, produtoRequest);
        logger.info("Produto atualizado com sucesso. ID: " + response.getProdutoId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Deletar produto",
            description = "Deleta um produto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        logger.info("Recebida requisição para deletar produto com ID: " + id);
        produtoServicePort.delete(id);
        logger.info("Produto com ID: " + id + " deletado com sucesso (lógica de deleção não implementada)");
        return ResponseEntity.noContent().build();
    }
}
