## Serviço de Mensageria:

#### Instalando o RabbitMQ:
Site: [rabbitMQ](https://www.rabbitmq.com)

##### Instalando via Docker:

```shell
docker run -it --name orderqueue -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

##### Acessando:
Após instalado, acessar: http://localhost:15672
Entrar com usuário e senha padrão: user: guest | senha: guest

##### Criando o Subscriber para a fila de emissão de cartões:
Adicionar nova fila no RabbitMQ (http://localhost:15672)
- Queues
    - Add a new queue
    - Coloque apenas o nome da fila: order-queue
    - Add queue

## CONSULTAR VIA BANCO
```sql
SELECT *
FROM order_entity
         JOIN public.product_entity pe on order_entity.id = pe.order_id
```