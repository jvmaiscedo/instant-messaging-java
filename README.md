
# HowUDoing Intant Messaging 

A aplicação de Instant Messaging desenvolvida em Java promove a interação entre usuários em máquinas distintas via rede utilizando-se da arquitetura cliente-servidor.

O projeto foi uma forma de avaliação proposta pelo professor da disciplina de Redes de Computadores para melhor absorção dos conceitos de comunicação de computadores via rede. A aplicação atual permite a prática dos conceitos estudados em sala.  

Desenvolvido em Java e com interface desktop utilizando-se do JavaFX nativo da versão 8 do Java, o HowUDoing promove a possibilidade de várias pessoas realizarem conversas de texto via rede em estruturas de grupos. A arquitetura utilizada foi a de cliente-servidor, que é uma arquitetura de simples implementação.


### Aprendizado e curiosidade

A aplicação foi pensada para comunicação entre processos de máquinas distintas desconsiderando suas plataformas computacionais, ou seja, de modo a ser possível que processos em diferentes plataformas computacionais se comuniquem sem perda de dados. Desde o inicio dos estudos de redes estudamos sobre protocolos de comunicação, desde à camada física - com as codificaçãoes Binária, Manchester e Manchester Diferencial (ISO/OSI) - até a camada de aplicação com as APDUS. A padronização é essencial para a comunicação correta entre as duas partes. 

No desenvolvimento do projeto acabei me deparando com um bug relacionado a padronização e achei interessante o bastante para comentá-lo aqui. No desonvolvimento foram criadas três APDUS: join, send e receive, de modo que a APDU do tipo join era para entrar em um grupo, ou, caso este não exista, criá-lo, e esta APDU era enviada via conexão TCP. Já as APDUS send e receive, como o nome sugere, eram utilizadas pelo cliente para enviar e receber mensagens via UDP.  No Java 8, na conexão UDP o pacote é montado e recebe, como um dos parâmetros, o vetor de bytes da mensagem. Enquanto que na conexão TCP, há o envio de objetos, que são codificados e decodificados na biblioteca da linguagem. 

Desse modo, quando tentei realizar a comunicação entre duas máquinas com SO diferentes, por exemplo, Linux e Windows, a criação do grupo era realizada corretamente - via TCP - mas as mensagens não conseguiam obter o mesmo êxito pois ao extrair os bytes em Linux obtia-os no padrão UTF-8, já no windows eram obtidos no padrão ANSI, de modo que o servidor, ao decodificar a APDU, não conseguia encaminhar a mensagem ao grupo correto, gerando um erro de comunicação. Apesar de simples solução - padronização dos bytes das mensagens - foi muito legal perceber que em todos os estágios da comunicação via rede os padrões de comunicação, protocolos, devem ser mantidos para garantir a comunicação eficaz e confiável.
### Demonstração do funcionamento
![Demonstração da execução da aplicação]
https://github.com/user-attachments/assets/0ec9ca4a-5785-45dc-a791-89b5097ec495

### Como utilizar a aplicação

Clone o repositório: `git clone: https://github.com/jvmaiscedo/instant-messaging-java.git`

É necessário possuir o Java, na versão 8, instalada.

Uma vez cumprido o pré-requisito, siga os seguintes passos para executar o servidor.

Estando no diretório raíz, abra o terminal e digite os comandos abaixo: 
```bash
cd Servidor
javac Principal.java
java Principal
```

Para executar o cliente:

```bash
cd Cliente
javac Principal.java
java Principal
```
