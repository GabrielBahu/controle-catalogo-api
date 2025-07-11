package com.br.controle_catalogo_api.domain.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.br.controle_catalogo_api.domain.dto.MensagemDTO;
import com.br.controle_catalogo_api.domain.port.out.aws.AwsSnsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwsSnsServiceImplTest {

    @Mock
    private AmazonSNS snsClient;

    @Mock
    private Topic catalogoTopic;

    @InjectMocks
    private AwsSnsServiceImpl awsSnsService;

    @Test
    @DisplayName("Publica mensagem no tópico SNS com sucesso")
    void publicaMensagemComSucesso() {
        String topicArn = "arn:aws:sns:us-east-1:123456789012:topico-eventos-catalogo";
        MensagemDTO mensagem = new MensagemDTO("mensagem");

        when(catalogoTopic.getTopicArn()).thenReturn(topicArn);

        awsSnsService.publicarMensagem(mensagem);

        verify(snsClient).publish(topicArn, mensagem.toString());
    }

    @Test
    @DisplayName("Publica mensagem vazia no tópico SNS")
    void publicaMensagemVaziaNoTopico() {
        String topicArn = "arn:aws:sns:us-east-1:123456789012:topico-eventos-catalogo";
        MensagemDTO mensagemVazia = new MensagemDTO("mensagem");

        when(catalogoTopic.getTopicArn()).thenReturn(topicArn);

        awsSnsService.publicarMensagem(mensagemVazia);

        verify(snsClient).publish(topicArn, mensagemVazia.toString());
    }

    @Test
    @DisplayName("Lança exceção quando mensagem é nula")
    void lancaExcecaoQuandoMensagemNula() {
        String topicArn = "arn:aws:sns:us-east-1:123456789012:topico-eventos-catalogo";
        when(catalogoTopic.getTopicArn()).thenReturn(topicArn);

        assertThrows(NullPointerException.class, () -> awsSnsService.publicarMensagem(null));
    }
}
