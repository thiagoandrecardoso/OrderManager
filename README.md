# ARQUIVOS:

- `eureka`
- `gateway`
- `order-process`
- `order-receipt`
- `order-receipt-calculate`

---

## **Resumo do Projeto**

Este projeto é composto por um ecossistema de microserviços para gerenciar pedidos, processar cálculos e manter o status dos pedidos sincronizados. A arquitetura utiliza o padrão REST com integração entre microserviços via RabbitMQ e FeignClient, garantindo alta escalabilidade e modularidade.

O fluxo principal do sistema é:
1. O serviço **`order-receipt`** recebe o pedido com uma lista de produtos, valida e salva o pedido.
2. Caso o pedido não exista, ele é salvo como **RECEBIDO** e enviado para uma fila no RabbitMQ.
3. O serviço **`order-process`** consome a fila, atualiza o status para **PROCESSING**, realiza cálculos, e atualiza o pedido com o status **CALCULATED**.
4. Por fim, os resultados são enviados ao serviço **`order-receipt-calculate`** através de FeignClient para serem registrados.

---

## **Tecnologias Utilizadas**

Abaixo estão as tecnologias e bibliotecas usadas, organizadas por microserviço.

---

### **1. order-receipt**

Este serviço é responsável por:
- Receber o pedido inicial.
- Validar os dados.
- Salvar o pedido no banco de dados.
- Publicar mensagens no RabbitMQ.

#### **Tecnologias**
- **Spring Boot**: Framework principal para desenvolvimento.
- **Spring Validation**: Para validação de DTOs, garantindo que os campos obrigatórios sejam informados.
- **Hibernate/JPA**: Para persistência de dados no banco.
- **RabbitMQ**: Para integração assíncrona, publicando mensagens sobre novos pedidos.
- **Lombok**: Redução de código boilerplate (getter/setter, `@ToString`, etc.).
- **RestController**: Para expor APIs RESTful.
- **Log4j2**: Para registro estruturado de logs.
- **MapStruct**: Para conversão entre entidades e DTOs.
- **RestControllerAdvice**: para centralizar o tratamento de erros.

---

### **2. order-process**

Este serviço consome mensagens de pedidos do RabbitMQ e processa os cálculos, atualizando o status e valores.

#### **Tecnologias**
- **Spring Boot**: Framework principal.
- **RabbitMQ Listener**: Para consumir mensagens de novas ordens.
- **FeignClient**: Para comunicação síncrona com o serviço `order-receipt-calculate`.
- **MapStruct**: Para conversão entre entidades e DTOs.
- **Hibernate/JPA**: Persistência de entidades.
- **Lombok**: Para reduzir código repetitivo.
- **Log4j2**: Para registro estruturado de logs.

---

### **3. order-receipt-calculate**

Este serviço recebe os resultados finais de cálculos e salva os valores.

#### **Tecnologias**
- **Spring Boot**: Framework principal.
- **FeignClient**: Para receber os cálculos do `order-process`.
- **Hibernate/JPA**: Para persistência dos dados.
- **Lombok**: Para facilitar a criação de DTOs e entidades.

---

### **4. gateway**

Este serviço age como uma porta de entrada para os microserviços, roteando requisições e centralizando a comunicação.

#### **Tecnologias**
- **Spring Cloud Gateway**: Para roteamento de requisições.
- **Spring Security**: Para proteção de rotas.
- **Eureka Client**: Para registro e descoberta de serviços.

---

### **5. eureka**

Serviço de registro para facilitar a descoberta de microserviços na arquitetura.

#### **Tecnologias**
- **Eureka Server**: Para gerenciamento de instâncias e registro de microserviços.
- **Spring Boot**: Para configurar o servidor.

---

## **Como Executar**

### **Pré-requisitos**
- **Java 17** ou superior.
- **RabbitMQ** em execução (porta padrão: `5672`).
- **PostgreSQL** como banco de dados principal.

### **Passo a Passo**
1. Clone este repositório:
   ```bash
   git clone <url-do-repositorio>
   cd <nome-do-projeto>
   ```
2. Configure as variáveis no `application.yml` de cada microserviço.
3. Suba os serviços na seguinte ordem:
    - `eureka`
    - `gateway`
    - `order-receipt`
    - `order-process`
    - `order-receipt-calculate`
4. Acesse o Eureka pela URL:
   ```bash
   http://localhost:8761
   ```

---

## **Arquitetura**

### **Diagrama do Fluxo**

1. **Cliente** envia uma requisição para o **gateway**.
2. O **gateway** roteia a requisição para o **order-receipt**.
3. O **order-receipt**:
    - Valida e salva o pedido.
    - Envia uma mensagem para o RabbitMQ.
4. O **order-process** consome a mensagem, processa os cálculos, e envia o resultado via FeignClient para o **order-receipt-calculate**.

---

### **Adicionando Containers Docker e Acessos**

#### **Serviço de Mensageria - RabbitMQ**

##### **Instalando via Docker**
Execute o seguinte comando para criar um container com o RabbitMQ e sua interface de gerenciamento:

```shell
docker run -it --name orderqueue -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

##### **Acessando o RabbitMQ**
- URL: [http://localhost:15672](http://localhost:15672)
- **Credenciais padrão**:
    - **Usuário**: `guest`
    - **Senha**: `guest`

##### **Criando a Fila**
1. Acesse a interface de gerenciamento do RabbitMQ.
2. Vá para a aba **Queues**.
3. Clique em **Add a new queue**.
4. Configure:
    - Nome da fila: `order-queue`.
5. Clique em **Add queue**.

---

#### **Banco de Dados**

##### **Consultando Pedidos e Produtos**
Execute a consulta abaixo para visualizar os pedidos e seus respectivos produtos no banco de dados:

```sql
SELECT *
FROM order_entity
         JOIN public.product_entity pe on order_entity.id = pe.order_id;
```

---

#### **Redis**

##### **Instalando via Docker**
Para subir um container Redis:

```shell
docker run --name my-redis -p 6379:6379 -d redis
```