# LiveChat
Esse projeto é uma segunda versão de um antigo projeto ([chat-gui](https://github.com/yuriteixeirac/chat-gui)) feito para a matéria de Programação Orientada a Objetos. Dessa vez, reescrito utilizando tecnologias diferentes e um melhor design da comunicação em tempo real.

## Tecnologias
- Java 21
- Spring 3
- MariaDB

## Mudanças
Em contraste com a primeira versão, a segunda traz uma REST API, que gerencia usuários e mensagens. Um sistema de autenticação mais robusto foi implementado, fornecido pelo Spring Security.

A API expõe um endpoint de conexão com a WebSocket, onde DTOs de mensagens podem trafegar e serem entregues para o usuário mencionado.

```
{
    "from": "juliahevellyn",
    "to": "yuriteixeirac",
    "content": "mensagem de teste.",
    "sentAt": "2026-02-17T17:08:19.050699108Z"
}
```
