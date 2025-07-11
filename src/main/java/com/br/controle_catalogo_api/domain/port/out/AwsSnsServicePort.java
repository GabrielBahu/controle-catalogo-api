package com.br.controle_catalogo_api.domain.port.out;

import com.br.controle_catalogo_api.domain.dto.MensagemDTO;

public interface AwsSnsServicePort {

    public void publicarMensagem(MensagemDTO mensagemDTO);
}
