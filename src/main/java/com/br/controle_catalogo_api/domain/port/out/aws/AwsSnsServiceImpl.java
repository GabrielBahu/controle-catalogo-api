package com.br.controle_catalogo_api.domain.port.out.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.br.controle_catalogo_api.domain.dto.MensagemDTO;
import com.br.controle_catalogo_api.domain.port.out.AwsSnsServicePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsServiceImpl implements AwsSnsServicePort {

    AmazonSNS snsClient;
    Topic catalogoTopic;

    public AwsSnsServiceImpl(AmazonSNS snsClient, @Qualifier("topicoEventosCatalogo") Topic catalogoTopic) {
        this.snsClient = snsClient;
        this.catalogoTopic = catalogoTopic;
    }

    @Override
    public void publicarMensagem(MensagemDTO mensagem) {
        this.snsClient.publish(catalogoTopic.getTopicArn(), mensagem.toString());
    }
}
